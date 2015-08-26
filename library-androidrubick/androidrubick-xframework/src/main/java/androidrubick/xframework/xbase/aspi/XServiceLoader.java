package androidrubick.xframework.xbase.aspi;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;

import androidrubick.io.IOUtils;

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
 *   for (MyService service : XServiceLoader<MyService>.load(MyService.class)) {
 *     if (service.supports(o)) {
 *       return service.handle(o);
 *     }
 *   }
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
public class XServiceLoader<S> implements Iterable<S> {

    private final Set<URL> services;
    private Class<S> service;
    private ClassLoader classLoader;

    /**
     * 子类需要对无参构造放开权限！
     */
    protected XServiceLoader() {
        this.services = new HashSet<URL>();
    }

    protected XServiceLoader<S> set(Class<S> service, ClassLoader classLoader) {
        // It makes no sense for service to be null.
        // classLoader is null if you want the system class loader.
        if (service == null) {
            throw new NullPointerException("service == null");
        }
        this.service = service;
        this.classLoader = classLoader;
        reload();
        return this;
    }

    /**
     * Invalidates the cache of known service provider class names.
     */
    public void reload() {
        internalLoad();
    }

    /**
     * Constructs a service loader, using the current thread's context class loader.
     *
     * @param service the service class or interface
     * @return a new XServiceLoader
     */
    public static <S>XServiceLoader<S> load(Class<S> service) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return checkAndLoadFrom(classLoader).set(service, classLoader);
    }

    /**
     * Constructs a service loader. If {@code classLoader} is null, the system class loader
     * is used.
     *
     * @param service the service class or interface
     * @param classLoader the class loader
     * @return a new XServiceLoader
     */
    public static <S>XServiceLoader<S> load(Class<S> service, ClassLoader classLoader) {
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
        return checkAndLoadFrom(classLoader).set(service, classLoader);
    }

    private static XServiceLoader checkAndLoadFrom(ClassLoader classLoader) {
        try {
            return XServiceLoader.load(XServiceLoader.class, classLoader).iterator().next();
        } catch (Exception e) {
            throw new IllegalStateException("no XServiceLoader provided");
        }
    }

    /**
     * 这里的机制可以被重写
     */
    protected void internalLoad() {
        services.clear();
        try {
            String name = "META-INF/services/" + service.getName();
            services.addAll(Collections.list(classLoader.getResources(name)));
        } catch (IOException e) {
            return;
        }
    }

    @Override
    public String toString() {
        return "XServiceLoader for " + service.getName();
    }

    @Override
    public Iterator<S> iterator() {
        return null;
    }

    protected class ServiceIterator implements Iterator<S> {
        private final ClassLoader classLoader;
        private final Class<S> service;
        private final Set<URL> services;

        private boolean isRead = false;

        private LinkedList<String> queue = new LinkedList<String>();

        public ServiceIterator(XServiceLoader<S> sl) {
            this.classLoader = sl.classLoader;
            this.service = sl.service;
            this.services = sl.services;
        }

        public boolean hasNext() {
            if (!isRead) {
                readClass();
            }
            return (queue != null && !queue.isEmpty());
        }

        @SuppressWarnings("unchecked")
        public S next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            String className = queue.remove();
            try {
                return service.cast(classLoader.loadClass(className).newInstance());
            } catch (Exception e) {
                throw new Error("Couldn't instantiate class " + className, e);
            }
        }

        private void readClass() {
            for (URL url : services) {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
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
                        if (!queue.contains(className)) {
                            queue.add(className);
                        }
                    }
                    isRead = true;
                } catch (Exception e) {
                    throw new Error("Couldn't read " + url, e);
                } finally {
                    IOUtils.close(reader);
                }
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void checkValidJavaClassName(String className) {
            for (int i = 0; i < className.length(); ++i) {
                char ch = className.charAt(i);
                if (!Character.isJavaIdentifierPart(ch) && ch != '.') {
                    throw new Error("Bad character '" + ch + "' in class name");
                }
            }
        }
    }
}
