package org.ixming.androidrubick.utils;

public class ReflectUtils {

	private ReflectUtils() { }
	
	/**
	 * 判断一个对象是target的子类实例
	 */
	public static boolean isTypeOf(Class<?> target, Object instance) {
		return isTypeOf(target, instance.getClass());
	}
	
	/**
	 * 判断一个对象是target的子类实例
	 */
	public static boolean isTypeOf(Class<?> targetClass, Class<?> instanceClass) {
		return targetClass.isAssignableFrom(instanceClass);
	}
	
}
