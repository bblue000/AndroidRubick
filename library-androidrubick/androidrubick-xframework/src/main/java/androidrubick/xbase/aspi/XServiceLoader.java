package androidrubick.xbase.aspi;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import androidrubick.collect.CollectionsCompat;
import androidrubick.io.IOUtils;
import androidrubick.text.Charsets;
import androidrubick.utils.Objects;
import androidrubick.xframework.app.XAppStateCallback;
import androidrubick.xframework.app.XGlobals;
import androidrubick.xframework.app.XAppStateMonitor;

/**
 * A service-provider loader.
 *
 * <p>A service provider is a factory for creating all known implementations of a particular
 * class or interface {@code S}. The known implementations are read from a configuration file in
 * {@code META-INF/services/}. The file's name should match the class' binary name (such as
 * {@code java.util.Outer$Inner}).
 *
 * <p>The file format is as follows.
 * The file's character encoding must be UTF-8.
 * Whitespace is ignored, and {@code #} is used to begin a comment that continues to the
 * next newline.
 * Lines that are empty after comment removal and whitespace trimming are ignored.
 * Otherwise, each line contains the binary name of one implementation class.
 * Duplicate entries are ignored, but entries are otherwise returned in order (that is, the file
 * is treated as an ordered set).
 *
 * <p>Given these classes:
 * <pre>
 * package a.b.c;
 * public interface MyService { ... }
 * public class MyImpl1 implements MyService { ... }
 * public class MyImpl2 implements MyService { ... }
 * </pre>
 * And this configuration file (stored as {@code META-INF/services/a.b.c.MyService}):
 * <pre>
 * # Known MyService providers.
 * a.b.c.MyImpl1  # The original implementation for handling "bar"s.
 * a.b.c.MyImpl2  # A later implementation for "foo"s.
 * </pre>
 * You might use {@code ServiceProvider} something like this:
 * <pre>
 *   MyService service = XServiceLoader<MyService>.load(MyService.class);
 *   service.handle(o);
 * </pre>
 *
 * <p>Note that each iteration creates new instances of the various service implementations, so
 * any heavily-used code will likely want to cache the known implementations itself and reuse them.
 * Note also that the candidate classes are instantiated lazily as you call {@code next} on the
 * iterator: construction of the iterator itself does not instantiate any of the providers.
 *
 * @param <S> the service class or interface
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/26.
 */
public class XServiceLoader<S extends XSpiService> {

    private static final boolean DEBUG = XGlobals.DEBUG;

    static final XAppStateCallback sAppStateCallback = new XAppStateCallback.SimpleAppStateCallback() {
        @Override
        public void onLowMemory() {
            trimMemory();
        }
    };
    static {
        XAppStateMonitor.registerAppStateCallback(sAppStateCallback);
    }

    /**
     * Constructs a service instance and cache it,
     * using the current app's context class loader.
     *
     * @param service the service class or interface
     * @return a XSpiService
     */
    public static <S extends XSpiService>S load(Class<S> service) {
        ClassLoader classLoader = XGlobals.getAppClassLoader();
        return findFromCacheOrCreate(service, classLoader);
    }

    /**
     * Constructs a service instance and cache it.
     * If {@code classLoader} is null, the system class loader is used.
     *
     * @param service the service class or interface
     * @param classLoader the class loader
     * @return a XSpiService
     */
    public static <S extends XSpiService>S load(Class<S> service, ClassLoader classLoader) {
        classLoader = Objects.getOr(classLoader, ClassLoader.getSystemClassLoader());
        return findFromCacheOrCreate(service, classLoader);
    }

    private static <S extends XSpiService>S findFromCacheOrCreate(Class<S> service, ClassLoader classLoader) {
        synchronized (sCaches) {
            XServiceLoader<S> serviceLoader;
            if (!CollectionsCompat.isEmpty(sCaches)) {
                serviceLoader = sCaches.get(service);
                if (!Objects.isNull(serviceLoader)) {
                    return serviceLoader.load();
                }
            }
            if (DEBUG) {
                serviceLoader = new XServiceLoaderForTest(service, classLoader);
            } else {
                serviceLoader = new XServiceLoader(service, classLoader);
            }
            sCaches.put(service, serviceLoader);
            return serviceLoader.load();
        }
    }

    /**
     * 当手机或者应用内存不足时，尝试调用，让缓存的服务清理各自的缓存
     */
    public static void trimMemory() {
        synchronized (sCaches) {
            if (!CollectionsCompat.isEmpty(sCaches)) {
                for (Map.Entry<Class<? extends XSpiService>, XServiceLoader> entry : sCaches.entrySet()) {
                    XServiceLoader value = entry.getValue();
                    value.trimMemory();
                }
            }
        }
    }

    private static HashMap<Class<? extends XSpiService>, XServiceLoader> sCaches
            = new HashMap<Class<? extends XSpiService>, XServiceLoader>(8);

    String mClassName;
    final Class<S> mService;
    final ClassLoader mClassLoader;
    S mCachedInstance;

    /**
     * 子类需要对无参构造放开权限！
     */
    protected XServiceLoader(Class<S> service, ClassLoader classLoader) {
        // It makes no sense for service to be null.
        // classLoader is null if you want the system class loader.
        if (service == null) {
            throw new NullPointerException("service == null");
        }
        this.mService = service;
        this.mClassLoader = classLoader;
        reload();
    }

    /**
     * Invalidates the cache of known service provider class names.
     */
    public void reload() {
        internalLoad();
    }

    /**
     * 这里的机制可以被重写
     */
    protected void internalLoad() {
        String name = "META-INF/services/" + mService.getName();
        final URL url = this.mClassLoader.getResource(name);
        if (Objects.isNull(url)) {
            throw new Error("Couldn't read service " + name);
        }
        this.mClassName = readClass(url, Charsets.UTF_8.name());
        if (Objects.isNull(this.mClassName)) {
            throw new Error("Couldn't read service class " + name);
        }
    }

    @Override
    public String toString() {
        return "XServiceLoader for " + mService.getName();
    }

    public S load() {
        S instance = mCachedInstance;
        if (Objects.isNull(instance)) {
            instance = newInstanceOfService();
            if (!instance.multiInstance()) {
                mCachedInstance = instance;
            }
        }
        return instance;
    }

    private String readClass(URL url, String charsetName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(url.openStream(), charsetName));
            String line;
            while ((line = reader.readLine()) != null) {
                // Strip comments and whitespace...
                int commentStart = line.indexOf('#');
                if (commentStart != -1) {
                    line = line.substring(0, commentStart);
                }
                line = line.trim();
                // Ignore empty lines.
                if (TextUtils.isEmpty(line)) {
                    continue;
                }
                String className = line;
                checkValidJavaClassName(className);
                return className;
            }
            return null;
        } catch (Exception e) {
            throw new Error("Couldn't read " + url, e);
        } finally {
            IOUtils.close(reader);
        }
    }

    private void checkValidJavaClassName(String className) {
        for (int i = 0; i < className.length(); ++i) {
            char ch = className.charAt(i);
            if (!Character.isJavaIdentifierPart(ch) && ch != '.') {
                throw new Error("Bad character '" + ch + "' in class name");
            }
        }
    }

    protected S newInstanceOfService() {
        String className = XServiceLoader.this.mClassName;
        try {
            return mService.cast(mClassLoader.loadClass(className).newInstance());
        } catch (Exception e) {
            throw new Error("Couldn't instantiate class " + className, e);
        }
    }

}
