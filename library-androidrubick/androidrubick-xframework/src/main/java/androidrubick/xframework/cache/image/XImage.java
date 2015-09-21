package androidrubick.xframework.cache.image;

import android.view.View;

import androidrubick.xbase.aspi.XServiceLoader;
import androidrubick.xframework.cache.image.spi.XImageService;

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

}
