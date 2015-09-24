package androidrubicktest.reflect;

import junit.framework.TestCase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import androidrubick.reflect.Reflects;

public class ReflectsTest extends TestCase {

    public void testGenDefaultMethodArgsByParameters() throws Exception {

    }

    public void testInvoke() throws Exception {
        Reflects.genDefaultMethodArgsByParameters(int.class, Object.class, byte[].class);
    }

    public void testInvokeThrow() throws Exception {

    }

    public void testGetDeclaredMehods() throws Exception {

    }

    public void testGetFieldValue() throws Exception {

    }

    public void testIsModifierPresent() throws Exception {

    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = ReflectsTest.class.getDeclaredMethod("d", new Class[]{String.class, Object[].class});
        m.invoke(null, null, null, null);
    }

    private static void d(String t, Object...p) {
        System.out.println(t);
        System.out.println(p);
    }

}