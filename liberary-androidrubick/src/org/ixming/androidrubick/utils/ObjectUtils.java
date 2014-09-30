package org.ixming.androidrubick.utils;

/**
 * 
 * utils for objects
 * 
 * @author Yin Yong
 *
 */
public class ObjectUtils {

	private ObjectUtils() { }
	
	/**
	 * 检查obj是否为null
	 */
	public static boolean checkNull(Object obj) {
		return null == obj;
	}
	
	/**
	 * 检查obj是否为null， 并抛出异常
	 */
	public static void checkNullAndThrow(Object obj, String msg) {
		if (checkNull(obj)) {
			throw new NullPointerException(msg + " : target is null");
		}
	}
	
	/**
	 * 判断对象是否equals
	 */
	public static boolean equals(Object obj1, Object obj2) {
		if (null == obj1) {
			return null == obj2;
		} else {
			return obj1.equals(obj2);
		}
	}
}
