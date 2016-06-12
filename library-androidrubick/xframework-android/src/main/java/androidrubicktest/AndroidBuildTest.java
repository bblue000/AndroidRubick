package androidrubicktest;

import android.os.Build;

import java.io.UnsupportedEncodingException;

/**
 * something
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/22.
 *
 * @since 1.0
 */
public class AndroidBuildTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
    }

    public static void testGet() {
        System.out.println("============AndroidBuildTest");
        // 机器版本号
        System.out.println("Build.ID = " + Build.ID);
        System.out.println("Build.DISPLAY = " + Build.DISPLAY);

        
        System.out.println("Build.PRODUCT = " + Build.PRODUCT);
        System.out.println("Build.DEVICE = " + Build.DEVICE);
        System.out.println("Build.BOARD = " + Build.BOARD);
        // 厂商，如Xiaomi
        System.out.println("Build.MANUFACTURER = " + Build.MANUFACTURER);
        // 品牌，如Xiaomi
        System.out.println("Build.BRAND = " + Build.BRAND);
        // 型号 MI 4W
        System.out.println("Build.MODEL = " + Build.MODEL);
        System.out.println("Build.BOOTLOADER = " + Build.BOOTLOADER);

        System.out.println("Build.HARDWARE = " + Build.HARDWARE);
        System.out.println("Build.SERIAL = " + Build.SERIAL);

        System.out.println("Build.VERSION.CODENAME = " + Build.VERSION.CODENAME);
        // android 系统发布版本，如2.3.3
        System.out.println("Build.VERSION.RELEASE = " + Build.VERSION.RELEASE);
        System.out.println("Build.VERSION.SDK = " + Build.VERSION.SDK);
        System.out.println("Build.VERSION.SDK_INT = " + Build.VERSION.SDK_INT);
        System.out.println("Build.VERSION.CODENAME = " + Build.VERSION.CODENAME);
    }

}
