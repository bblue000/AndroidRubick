package androidrubicktest;

import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;

import androidrubick.utils.Exceptions;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/22.
 */
public class ExceptionsTest {

    private ExceptionsTest() { /* no instance needed */ }

    public static void main(String args[]) throws Throwable {

        Exceptions.toRuntime("1").printStackTrace();

        System.out.println();

        try {
            throw new UnsupportedEncodingException("");
        } catch (Exception e) {
            Throwable my = Exceptions.toRuntime(e);
            my.printStackTrace();
            throw my;
        }

    }

}
