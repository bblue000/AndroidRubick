package androidrubick.xframework.image;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;

import androidrubick.utils.Objects;
import androidrubick.xbase.util.DeviceInfos;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/7.
 */
public class BitmapUtils {

    private BitmapUtils() { /* no instance needed */ }

    /**
     * 获取<code>bm</code>的字节大小
     */
    @SuppressLint("NewApi")
    public static int getByteCount(Bitmap bm) {
        if (Objects.isNull(bm)) {
            return 0;
        }
        if (DeviceInfos.getAndroidSDKVersion() < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bm.getRowBytes() * bm.getHeight();
        } else if (DeviceInfos.getAndroidSDKVersion() < Build.VERSION_CODES.KITKAT) {
            return bm.getByteCount();
        } else {
            return bm.getAllocationByteCount();
        }
    }

}
