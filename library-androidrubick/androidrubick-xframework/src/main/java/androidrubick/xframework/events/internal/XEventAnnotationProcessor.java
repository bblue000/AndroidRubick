package androidrubick.xframework.events.internal;

import java.lang.reflect.Method;

import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;
import androidrubick.utils.Reflects;
import androidrubick.xframework.events.annotation.XEvent;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/12 0012.
 */
public class XEventAnnotationProcessor {

    static final String PATH_PREFIX_JAVA = "java.";
    static final String PATH_PREFIX_JAVAX = "javax.";
    static final String PATH_PREFIX_ANDROID = "android.";
    static final String PATH_PREFIX_ANDROIDRUBICK = "androidrubick.";

    public static void inject(Object target) {
        Preconditions.checkNotNull(target, "target object is null");
        Class<?> targetClass = target.getClass();
        injectTargetClassMethods(target, targetClass);
    }

    public static void post(String action) {
        post(action, null);
    }

    public static void post(String action, Object data) {
        XEventBus.getInstance().post(action, data);
    }

    static void injectTargetClassMethods(Object target, Class<?> targetClass) {
        Class<?> curClass = targetClass;
        while (!shouldFilterClass(curClass)) {
            Method[] methods = Reflects.getDeclaredMehods(curClass);
            if (!Objects.isEmpty(methods)) {
                for (Method method : methods) {
                    injectTargetMethod(target, targetClass, method);
                }
            }
            curClass = curClass.getSuperclass();
        }
    }

    static void injectTargetMethod(final Object target, final Class<?> targetClass, final Method method) {
        XEvent methodXEventAnnotation = method.getAnnotation(XEvent.class);
        if (Objects.isNull(methodXEventAnnotation)) {
            return;
        }
        String eventActions[] = methodXEventAnnotation.value();
        Preconditions.checkArgument(!Objects.isEmpty(eventActions), "XEvent should define value");
        XEventBus.getInstance().register(new Subscription(target, method), (Object[]) eventActions);
    }

    static boolean shouldFilterClass(Class<?> targetClass) {
        String className = targetClass.getName();
        if (className.startsWith(PATH_PREFIX_ANDROID)
                || className.startsWith(PATH_PREFIX_ANDROIDRUBICK)
                || className.startsWith(PATH_PREFIX_JAVA)
                || className.startsWith(PATH_PREFIX_JAVAX)
                ) {
            return true;
        }
        return false;
    }

}
