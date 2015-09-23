package androidrubick.xframework.cache.image;

import android.graphics.Bitmap;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/18.
 */
public interface XImageLoadCallback {

    /**
     * 准备加载图片时回调
     */
    void onLoadStart(String imageUri);

    /**
     * 图片加载成功时回调
     */
    void onLoadSuccess(String imageUri, Bitmap bitmap);

    /**
     * 图片加载失败时回调
     */
    void onLoadFailed(String imageUri);

}
