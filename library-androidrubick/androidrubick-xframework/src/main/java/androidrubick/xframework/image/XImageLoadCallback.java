package androidrubick.xframework.image;

import android.graphics.Bitmap;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/18.
 */
public interface XImageLoadCallback {

    /**
     * 从内存缓存中获取
     */
    public static int MEM = 0x1;
    /**
     * 从文件缓存中获取
     */
    public static int DISK = 0x2;
    /**
     * 从网络获取
     */
    public static int REMOTE = 0x3;

    /**
     * 准备加载图片时回调
     */
    void onLoadStart(String imageUri);

    /**
     * 图片加载成功时回调
     *
     * @param loadedFrom one of {@link #MEM}, {@link #DISK}, {@link #REMOTE}
     */
    void onLoadSuccess(String imageUri, Bitmap bitmap, int loadedFrom);

    /**
     * 图片加载失败时回调
     */
    void onLoadFailed(String imageUri);

}
