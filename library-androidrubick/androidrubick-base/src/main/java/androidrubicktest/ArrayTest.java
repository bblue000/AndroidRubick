package androidrubicktest;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/7/11 0011.
 *
 * @since 1.0
 */
public class ArrayTest {

    public static void main(String args[]) {
        System.out.println("" + (Object[].class.isAssignableFrom(String[].class)));


        Object[] argsObjArr = args;
        System.out.println("" + (Object[].class.isAssignableFrom(String[].class)));


    }

}
