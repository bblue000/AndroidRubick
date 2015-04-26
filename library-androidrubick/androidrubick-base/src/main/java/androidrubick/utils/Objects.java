package androidrubick.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Helper functions that can operate on any {@code Object}.
 * <br/>
 * 对所有Object对象试用的工具类
 *
 * <p/>
 *
 * Created by yong01.yin on 2014/12/30.
 */
public class Objects {

    private Objects() {}

    /**
     * Returns true if both arguments are null,
     * the result of {@link java.util.Arrays#equals} if both arguments are primitive arrays,
     * the result of {@link java.util.Arrays#deepEquals} if both arguments are arrays of reference types,
     * and the result of {@link #equals} otherwise.
     */
    public static boolean deepEquals(Object a, Object b) {
        if (a == null || b == null) {
            return a == b;
        } else if (a instanceof Object[] && b instanceof Object[]) {
            return Arrays.deepEquals((Object[]) a, (Object[]) b);
        } else if (a instanceof boolean[] && b instanceof boolean[]) {
            return Arrays.equals((boolean[]) a, (boolean[]) b);
        } else if (a instanceof byte[] && b instanceof byte[]) {
            return Arrays.equals((byte[]) a, (byte[]) b);
        } else if (a instanceof char[] && b instanceof char[]) {
            return Arrays.equals((char[]) a, (char[]) b);
        } else if (a instanceof double[] && b instanceof double[]) {
            return Arrays.equals((double[]) a, (double[]) b);
        } else if (a instanceof float[] && b instanceof float[]) {
            return Arrays.equals((float[]) a, (float[]) b);
        } else if (a instanceof int[] && b instanceof int[]) {
            return Arrays.equals((int[]) a, (int[]) b);
        } else if (a instanceof long[] && b instanceof long[]) {
            return Arrays.equals((long[]) a, (long[]) b);
        } else if (a instanceof short[] && b instanceof short[]) {
            return Arrays.equals((short[]) a, (short[]) b);
        }
        return a.equals(b);
    }

    /**
     * Null-safe equivalent of {@code a.equals(b)}.
     */
    public static boolean equals(Object a, Object b) {
        return (a == null) ? (b == null) : a.equals(b);
    }

    /**
     * Convenience wrapper for {@link Arrays#hashCode}, adding varargs.
     * This can be used to compute a hash code for an object's fields as follows:
     * {@code Objects.hash(a, b, c)}.
     */
    public static int hash(Object... values) {
        return Arrays.hashCode(values);
    }

    /**
     * Returns 0 for null or {@code o.hashCode()}.
     */
    public static int hashCode(Object o) {
        return (o == null) ? 0 : o.hashCode();
    }

    /**
     * Returns "null" for null or {@code o.toString()}.
     */
    public static String toString(Object o) {
        return toString(o, "null");
    }

    /**
     * Returns {@code nullString} for null or {@code o.toString()}.
     */
    public static String toString(Object o, String nullString) {
        return (o == null) ? nullString : o.toString();
    }

    public static boolean isNull(Object o) {
        return null == o;
    }

    /**
     * 判断一个对象是否为“空”——null 或者是对象意义上的空，如空字符串
     */
    public static boolean isEmpty(Object o) {
        if (isNull(o)) {
            return true;
        }
        if (o instanceof CharSequence) {
            return getAs(o, CharSequence.class).length() == 0;
        }
        if (o.getClass().isArray()) {
            return Array.getLength(o) == 0;
        }
        if (o instanceof Collection) {
            return getAs(o, Collection.class).isEmpty();
        }
        if (o instanceof Map) {
            return getAs(o, Map.class).isEmpty();
        }
        return false;
    }

    public static <T> T getAs(Object o) {
        if (null == o) {
            return (T) null;
        }
        try {
            return (T) o;
        } catch (Exception e) {
            return (T)null;
        }
    }

    public static <T> T getAs(Object o, Class<T> clz) {
        if (null == o) {
            return (T) null;
        }
        try {
            return (T) o;
        } catch (Exception e) {
            return (T)null;
        }
    }

    public static <T>T defValue(Class<T> type) {
        return (T) Primitives.defValueOf(type);
    }
}
