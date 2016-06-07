package androidrubick.xframework.view;

import android.net.Uri;
import android.text.TextUtils;

import java.io.File;

/**
 *
 * 工具类
 *
 * Created by Yin Yong on 16/3/24.
 */
public class Images {

    private Images() { }

    /**
     * 网络资源文件
     */
    public static Uri url(String url) {
        if (TextUtils.isEmpty(url)) {
            return Uri.parse("");
        } else {
            return Uri.parse(url);
        }
    }

    /**
     * assets目录中的文件
     */
    public static Uri asset(String path) {
        if (TextUtils.isEmpty(path)) {
            return Uri.parse("");
        } else {
            return Uri.parse("asset:///" + path);
        }
    }

    /**
     * 绝对路径的文件
     */
    public static Uri file(String absPath) {
        if (TextUtils.isEmpty(absPath)) {
            return Uri.parse("");
        } else {
            return Uri.parse("file://" + absPath);
        }
    }

    /**
     * 绝对路径的文件
     */
    public static Uri file(File absPath) {
        if (null == absPath) {
            return Uri.parse("");
        }
        return Uri.fromFile(absPath);
    }

    /**
     * res资源文件
     */
    public static Uri res(int resId) {
        return Uri.parse("res:///" + resId);
    }

}
