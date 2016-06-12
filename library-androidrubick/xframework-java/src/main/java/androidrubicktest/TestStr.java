package androidrubicktest;

/**
 * something
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/10/20.
 *
 * @since 1.0
 */
public class TestStr {

    public static void main(String args[]) {
        char[] cs = new char[3];
        cs[0] = '我';
        cs[1] = '我';
        cs[2] = '我';

        String str1;
        System.out.println(str1 = new String(cs));
        cs[1] = '是';
        System.out.println(str1);
        System.out.println(new String(cs));
    }

}
