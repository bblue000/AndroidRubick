package androidrubick.xframework.cache.image;

import androidrubick.xbase.aspi.XServiceLoader;
import androidrubick.xframework.cache.image.spi.XImageService;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/21.
 */
public class XImageOptions {

    /**
     * 最大内存，如果大于0，则返回的Bitmap会进行最大内存的限制。
     *
     * <p/>
     *
     * 这是限制加载的Bitmap的大小，并非显示图片的控件的大小
     */
    public int maxSize;

    /**
     * 最大宽度，如果大于0，则返回的Bitmap会进行最大宽度的限制。
     *
     * <p/>
     *
     * 这是限制加载的Bitmap的大小，并非显示图片的控件的大小
     */
    public int maxWidth;

    /**
     * 最大高度，如果大于0，则返回的Bitmap会进行最大高度的限制。
     *
     * <p/>
     *
     * 这是限制加载的Bitmap的大小，并非显示图片的控件的大小
     */
    public int maxHeight;

    public XImageSetter imageSetter;

    public XImageLoadCallback imageLoadCallback;

    public XImageOptions() {
        XServiceLoader.load(XImageService.class).configImageOptions(this);
    }

    public XImageOptions maxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    public XImageOptions maxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public XImageOptions maxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public XImageOptions imageSetter(XImageSetter imageSetter) {
        this.imageSetter = imageSetter;
        return this;
    }

    public XImageOptions imageLoadCallback(XImageLoadCallback imageLoadCallback) {
        this.imageLoadCallback = imageLoadCallback;
        return this;
    }

}
