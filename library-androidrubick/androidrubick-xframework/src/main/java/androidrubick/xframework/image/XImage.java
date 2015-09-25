package androidrubick.xframework.image;

import android.view.View;

import androidrubick.xbase.aspi.XServiceLoader;
import androidrubick.xframework.image.spi.XImageService;

/**
 *
 * 图片缓存所做的事包括：
 * 提供途径获取图片，封装内容包括缓存机制和异步机制；
 * 提供方式设置图片；
 * 提供开始加载图片，成功加载图片和加载图片失败的回调。
 *
 * <p/>
 *
 * 至于图片载体的大小，不是这边该管的事
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/15.
 */
public class XImage {

    /**
     * 加载图片
     *
     * @param uri 图片Uri
     * @param callback 加载图片的回调
     */
    public static void fromUrl(String uri, XImageLoadCallback callback) {

    }

    /**
     * 加载图片
     *
     * @param uri 图片Uri
     * @param options 加载图片的回调
     */
    public static void fromUrl(String uri, XImageOptions options) {

    }

    /**
     * 加载图片
     *
     * @param filePath 图片路径
     * @param callback 加载图片的回调
     */
    public static void fromFile(String filePath, XImageLoadCallback callback) {

    }

    /**
     * 加载图片
     *
     * @param res 图片资源id
     * @param options 加载图片的回调
     */
    public static void fromRes(int res, XImageOptions options) {

    }

    /**
     * 加载图片
     *
     * @param fileName 图片Uri
     * @param callback 加载图片的回调
     */
    public static void fromAsset(String fileName, XImageLoadCallback callback) {

    }

    public static <V extends View>void displayImage(String uri, V view) {
        XServiceLoader.load(XImageService.class).loadBitmap(view, uri, null);
    }

    public static <V extends View>void displayImage(String uri, V view, XImageSetter setter) {
        XServiceLoader.load(XImageService.class).loadBitmap(view, uri, setter, null);
    }

    public static <V extends View>void displayImage(String uri, V view, XImageLoadCallback callback) {
        XServiceLoader.load(XImageService.class).loadBitmap(view, uri, null, callback);
    }

    public static <V extends View>void displayImage(String uri, V view,
                                                    XImageSetter setter, XImageLoadCallback callback) {
        XServiceLoader.load(XImageService.class).loadBitmap(view, uri, setter, callback);
    }

    public static <V extends View>void displayImage(String uri, V view, XImageOptions options) {
        XServiceLoader.load(XImageService.class).loadBitmap(view, uri, options);
    }

//    public static

}
