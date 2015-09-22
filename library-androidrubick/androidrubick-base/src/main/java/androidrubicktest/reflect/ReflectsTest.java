package androidrubicktest.reflect;

import junit.framework.TestCase;

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
}