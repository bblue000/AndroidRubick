package androidrubick.xframework.cache.image.spi;

import android.view.View;

import androidrubick.xbase.aspi.XSpiService;
import androidrubick.xframework.cache.image.XImageLoadCallback;
import androidrubick.xframework.cache.image.XImageOptions;
import androidrubick.xframework.cache.image.XImageSetter;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/16.
 */
public interface XImageService extends XSpiService {

    public void loadBitmap(View view, String uri, XImageSetter imageSetter, XImageLoadCallback callback);

    public void loadBitmap(View view, String uri, XImageOptions options);

    public void configImageOptions(XImageOptions options);

}