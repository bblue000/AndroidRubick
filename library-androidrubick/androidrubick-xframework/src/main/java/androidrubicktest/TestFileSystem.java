package androidrubicktest;

import android.os.Environment;
import android.util.Log;

import androidrubick.xframework.app.XApplication;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/7.
 */
public class TestFileSystem {

    private TestFileSystem() { /* no instance needed */ }

    public static void main(String args[]) {



    }


    public static void test() {
        // /data
        Log.e("yytest", "getDataDirectory = " + Environment.getDataDirectory());
        // /system
        Log.e("yytest", "getRootDirectory = " + Environment.getRootDirectory());
        // /storage/emulated/0
        Log.e("yytest", "getExternalStorageDirectory = " + Environment.getExternalStorageDirectory());
        // /storage/emulated/0/DCIM
        Log.e("yytest", "getExternalStoragePublicDirectory = " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));

        // /data/data/org.androidrubick.demo/cache
        Log.e("yytest", "getCacheDir = " + XApplication.getAppContext().getCacheDir());
        // /storage/emulated/0/Android/data/org.androidrubick.demo/cache
        Log.e("yytest", "getExternalCacheDir = " + XApplication.getAppContext().getExternalCacheDir());
    }


}
