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


        d((String) null);
        d((String[]) null);
        d();
        String[] arr = null;
        d(arr);
    }

    private static void d(String...args) {
        if (null == args) {
            System.out.println("不可能执行这里?");
            return;
        }
        System.out.println("" + args.length);
    }

}
