package androidrubick.xframework.image;

import android.graphics.BitmapFactory;

import androidrubick.xbase.aspi.XServiceLoader;
import androidrubick.xframework.image.spi.XImageService;

/**
 *
 * 加载图片的大小、内存占用等信息的设置项。
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/21.
 */
public class XImageOptions {

    /**
     * 根据{@link android.graphics.BitmapFactory.Options}返回一个对象，接下来仍可以调用
     *
     * 方法设置其他参数
     */
    public static XImageOptions by(BitmapFactory.Options options) {
        return new XImageOptions().bmOptions(options);
    }

    public static XImageOptions newImageOptions() {
        return new XImageOptions();
    }

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

    /**
     * 用于加载Bitmap的{@link android.graphics.BitmapFactory.Options}
     */
    public BitmapFactory.Options options;

    /**
     * 回调
     */
    public XImageLoadCallback imageLoadCallback;

    /**
     * TODO 这边先隐藏构造，此处可能有变动
     */
    private XImageOptions() {
        XServiceLoader.load(XImageService.class).configImageOptions(this);
    }

    /**
     * 设置最大内存
     */
    public XImageOptions maxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    /**
     * 设置最大宽度
     */
    public XImageOptions maxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    /**
     * 设置最大高度
     */
    public XImageOptions maxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    /**
     * 设置用于加载Bitmap的{@link android.graphics.BitmapFactory.Options}
     */
    public XImageOptions bmOptions(BitmapFactory.Options options) {
        this.options = options;
        return this;
    }

    /**
     * 设置加载回调
     */
    public XImageOptions imageLoadCallback(XImageLoadCallback imageLoadCallback) {
        this.imageLoadCallback = imageLoadCallback;
        return this;
    }

}
