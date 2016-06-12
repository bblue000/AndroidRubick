package androidrubicktest;

import androidrubick.utils.MathCompat;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/8.
 */
public class MathTest {

    private MathTest() { /* no instance needed */ }

    public static void main(String[] args) {
        System.out.println("" + MathCompat.limitByRange(10, 0, 9));
        System.out.println("" + MathCompat.limitByRange(10, -1, -9));
        System.out.println("" + MathCompat.limitByRange(10, -9, -1));

        System.out.println("" + MathCompat.limitByRange(10, -9, -1, 100));
        System.out.println("" + MathCompat.limitByRange(10, -9, 10, 100));
    }

}
