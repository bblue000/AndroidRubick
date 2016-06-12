package androidrubicktest.io;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;

import androidrubick.io.FileProgressCallback;
import androidrubick.io.FileUtils;
import androidrubick.io.ZipUtils;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/25 0025.
 *
 * @since 1.0
 */
public class FileUtilsTest {

    private FileUtilsTest() { /* no instance needed */ }

    public static void main(String[] args) throws IOException {
//        testCreateFile();
//        testCreateDir();
//        testDelFile();
//        testSaveToFile();
//        testCopyToFile();
//        copyDir();
//        unzip();
//        calFir();



        System.out.println(FileUtils.calFileSizeString(Integer.MAX_VALUE));
        System.out.println(String.format("%s", 100));
        FileUtils.stringToFile(new File("D://yytest1.txt"),
                (FileUtils.readTextFile(new File("D://yytest5"), 100, "...", "GBK")),
                "GBK", false);

        String fileText = FileUtils.readTextFile(new File("D://yytest1.txt"), 100, "...", "GBK");
        System.out.println("fileText = " + fileText);
        char[] chars = fileText.toCharArray();
        for (char c : chars) {
            System.out.print(Integer.toBinaryString(0x0000ffff & ((int) c)));
            System.out.print(" ");
        }
        System.out.println();
        chars = "我是中国人".toCharArray();
        for (char c : chars) {
            System.out.print(Integer.toBinaryString(0x0000ffff & ((int) c)));
            System.out.print(" ");
        }
        System.out.println();
        chars = fileText.toCharArray();
        for (char c : chars) {
            System.out.print(Integer.toHexString(0x0000ffff & ((int) c)));
            System.out.print(" ");
        }
        System.out.println();
        chars = "我是中国人".toCharArray();
        for (char c : chars) {
            System.out.print(Integer.toHexString(0x0000ffff & ((int) c)));
            System.out.print(" ");
        }
        System.out.println();
        System.out.println();









        byte[] buf = fileText.getBytes("GBK");
        for (byte b : buf) {
            System.out.print(Integer.toBinaryString(0x000000ff & ((int) b)));
            System.out.print(" ");
        }
        System.out.println();
        buf = "我是中国人".getBytes();
        for (byte b : buf) {
            System.out.print(Integer.toBinaryString(0x000000ff & ((int) b)));
            System.out.print(" ");
        }
        System.out.println();

        buf = fileText.getBytes("GBK");
        for (byte b : buf) {
            System.out.print(Integer.toHexString(0x000000ff & ((int) b)));
            System.out.print(" ");
        }
        System.out.println();
        buf = "我是中国人".getBytes();
        for (byte b : buf) {
            System.out.print(Integer.toHexString(0x000000ff & ((int) b)));
            System.out.print(" ");
        }
        System.out.println();

        System.out.println();
        System.out.println(Integer.toBinaryString(0x0000ffff & ((int) '\u6211')));




//        FileUtils.stringToFile(new File("D://yytest3"), "我是中国人", "UTF-8", false);
    }

    private static void calFir() {
        int result[] = FileUtils.calculateFileAndDirCount(new File("D:\\GitHub\\myDocs"), true, false);
        System.out.println(" calFir ? " + result[0]);
        System.out.println(" calFir ? " + result[1]);
        System.out.println(" calFir ? " + result[2]);
    }

    private static void unzip() throws IOException {
//        System.out.println(" unzip ? " +
//            ZipUtils.unzip(new File("C:/yytest.zip"), false, new File("C:/yytest.data"), false, null)
//                + "" );
        System.out.println(" unzip ? " +
            ZipUtils.unzip(new File("C:/yytest.zip"), false, new File("C:/yytest.data"), true, null)
                + "" );
    }

    private static void copyDir() throws IOException {
//        System.out.println(" copyDir ? "
//                + FileUtils.copyDir(new File("C:/yytest.data"), true,
//                new File("C:/yytest.data1"), true, null, null));

//        System.out.println(" copyToFile ? "
//                + FileUtils.copyToFile(new File("C:/yytest.data"), true,
//                new File("C:/yytest.data1"), false, null, null));
//        System.out.println(" copyToFile ? "
//                + FileUtils.copyToDir(new File("C:/yytest.data"), true,
//                new File("C:/yytest.data1"), false, null, null));



        System.out.println(" copyToFile ? "
                + FileUtils.copyToFile(new File("C:/yytest.data"), true,
                new File("C:/yytest.data1"), true, null, null));
    }

    private static void testCopyToFile() throws IOException {
//        System.out.println(" copyToFile ? "
//                + FileUtils.copyToFile(new File("D:/yytest.data"),
//                new File("D:/yytest.data1"), false, null, null));
//        System.out.println(" copyToFile ? "
//                + FileUtils.copyToDir(new File("D:/yytest.data"),
//                new File("D:/yytest.data1"), false, null, null));
    }

    private static void testSaveToFile() throws IOException {
        System.out.println(" saveToFile ? "
                + FileUtils.saveToFile(new StringReader("123\n"), true,
                new File("D:/yytest.data"), false, null, null, null));
    }

    private static void testDelFile() {
        System.out.println("deleteFile = " + FileUtils.deleteFile(new File("D:/yytest.data"), false, new FileProgressCallback() {
            @Override
            public void onProgress(File file, boolean success) {
                System.out.println(file + " del ? " + success);
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        }));
    }

    private static void testCreateDir() {
        // tested cases:
        // file not exist
        // file exists
        // target is dir
        System.out.println("createDir = " + FileUtils.createDir(new File("D:/yytest.data")));
        System.out.println("createDir = " + FileUtils.createDir(new File("D:/yytest.data")));
    }

    private static void testCreateFile() {
        // tested cases:
        // file not exist
        // file exists
        // target is dir
        System.out.println("createFile = " + FileUtils.createFile(new File("D:/yytest.data")));
        System.out.println("createFile = " + FileUtils.createFile(new File("D:/yytest.data")));
    }


}