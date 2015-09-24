package androidrubicktest.io;

import java.io.File;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/24 0024.
 *
 * @since 1.0
 */
public class FileTest3 {

    private FileTest3() { /* no instance needed */ }

    public static void main(String[] args) {
        File file = new File("D://");

        File file1 = new File(file, "");

        System.out.println(file1.getAbsolutePath());
        System.out.println(file1.exists());
    }

}