package androidrubick.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * 反射的工具类
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/11 0011.
 */
public class Reflects {

    private Reflects() { /* no instance needed */ }

    /**
     * 根据参数类型数组<code>parameters</code>返回带有对应默认值的值数组
     * @param parameters 参数类型数组
     * @return 带有对应默认值的值数组
     */
    public static Object[] genDefaultMethodArgsByParameters(Class<?>...parameters) {
        if (null == parameters || parameters.length == 0) {
            return null;
        }
        Object[] values = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            values[i] = Primitives.defValueOf(parameters[i]);
        }
        return values;
    }

    /**
     * 调用方法
     *
     * @param target 需要调用的方法所在的对象
     * @param method 方法
     * @param params 调用方法传入的参数
     * @param <Result> 泛型的返回类型
     * @return 如果方法调用完，返回方法对应的返回值；如果有异常则返回null
     */
    public static <Result>Result invoke(Object target, Method method, Object...params) {
        try {
            boolean isAccessible = method.isAccessible();
            if (!isAccessible) {
                method.setAccessible(true);
            }
            Result result = (Result) method.invoke(target, params);
            if (!isAccessible) {
                method.setAccessible(isAccessible);
            }
            return result;
        } catch (Exception e) {
            return (Result) null;
        }
    }

    /**
     * 获取<code>clz</code>中申明的所有方法
     * @param clz 目标类
     * @return <code>clz</code>中申明的所有方法，如果clz为null，返回null
     */
    public static Method[] getDeclaredMehods(Class<?> clz) {
        try {
            return clz.getDeclaredMethods();
        } catch (Exception e) {
            return null;
        }
    }

}