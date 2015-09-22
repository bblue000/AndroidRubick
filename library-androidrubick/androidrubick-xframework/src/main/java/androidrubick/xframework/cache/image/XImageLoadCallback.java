package androidrubick.xframework.cache.image;

import android.graphics.Bitmap;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/18.
 */
public interface XImageLoadCallback {

    void onLoadStart();

    void onLoadSuccess(Bitmap bitmap);

    void onLoadFailed();

    void onLoadCanceled(Bitmap bitmap);


}
