package androidrubicktest.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

import androidrubick.io.FileUtils;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/13 0013.
 *
 * @since 1.0
 */
public class FileTest2 {

    private FileTest2() { /* no instance needed */ }

    public static void main(String[] args) throws IOException {
        System.out.println(Integer.toBinaryString(1));
        System.out.println(Integer.toBinaryString('A'));
        System.out.println(Charset.defaultCharset());
        System.out.println(Locale.getDefault());
        System.out.println(FileUtils.readTextFile(new File("D://yytest.txt"), 100, "...", "GBK"));
        System.out.println(FileUtils.readTextFile(new File("D://yytest5"), 100, "...", "GBK"));
//        FileUtils.stringToFile(new File("D://yytest5"), "��", "GBK", false);




    }

}