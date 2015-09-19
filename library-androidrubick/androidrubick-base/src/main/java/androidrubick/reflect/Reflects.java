package androidrubick.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import androidrubick.utils.Exceptions;
import androidrubick.utils.Objects;

/**
 * 反射的工具类
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/11 0011.
 *
 * @since 1.0
 */
public class Reflects {

    private Reflects() { /* no instance needed */ }

    /**
     * 根据参数类型数组<code>parameters</code>返回带有对应默认值的值数组
     * @param parameters 参数类型数组
     * @return 带有对应默认值的值数组
     *
     * @since 1.0
     */
    public static Object[] genDefaultMethodArgsByParameters(Class<?>...parameters) {
        if (null == parameters || parameters.length == 0) {
            return null;
        }
        Object[] values = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            values[i] = Objects.defValue(parameters[i]);
        }
        return values;
    }

//    /**
//     * 从<code>clz</code>指定的类遍历查找到{@linkplain java.lang.Object}
//     * @param clz 指定类
//     * @param methodName 方法名
//     * @param params 方法参数类型
//     * @return
//     */
//    public static Method getDeclaredMehod(Class<?> clz, String methodName, Class<?>...params) {
//
//    }

    /**
     * 调用方法
     *
     * @param target 需要调用的方法所在的对象
     * @param method 方法
     * @param params 调用方法传入的参数
     * @param <Result> 泛型的返回类型
     * @return 如果方法调用完，返回方法对应的返回值；如果有异常则返回null
     *
     * @since 1.0
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
     * 调用方法，将会抛出方法调用的异常
     *
     * @param target 需要调用的方法所在的对象
     * @param method 方法
     * @param params 调用方法传入的参数
     * @param <Result> 泛型的返回类型
     * @return 如果方法调用完，返回方法对应的返回值；如果有异常则返回null
     *
     * @since 1.0
     */
    public static <Result>Result invokeThrow(Object target, Method method, Object...params) throws RuntimeException {
        boolean isAccessible = method.isAccessible();
        if (!isAccessible) {
            method.setAccessible(true);
        }
        Result result;
        try {
            result = (Result) method.invoke(target, params);
        } catch (Exception e) {
            throw Exceptions.toRuntime(e);
        } finally {
            if (!isAccessible) {
                method.setAccessible(isAccessible);
            }
        }
        return result;
    }

    /**
     * 获取<code>clz</code>中申明的所有方法
     * @param clz 目标类
     * @return <code>clz</code>中申明的所有方法，如果clz为null，返回null
     *
     * @since 1.0
     */
    public static Method[] getDeclaredMehods(Class<?> clz) {
        try {
            return clz.getDeclaredMethods();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取字段值
     */
    public static Object getFieldValue(Object target, Field field) {
        boolean isAccessible = field.isAccessible();
        if (!isAccessible) {
            field.setAccessible(true);
        }
        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            throw Exceptions.toRuntime(e);
        }
    }

}