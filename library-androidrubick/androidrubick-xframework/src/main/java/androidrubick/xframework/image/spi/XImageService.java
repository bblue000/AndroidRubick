package androidrubick.xframework.image.spi;

import android.view.View;

import androidrubick.xbase.aspi.XSpiService;
import androidrubick.xframework.image.XImageLoadCallback;
import androidrubick.xframework.image.XImageOptions;
import androidrubick.xframework.image.XImageSetter;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/16.
 */
public interface XImageService extends XSpiService {

    public void loadBitmap(String uri, XImageLoadCallback callback);

    public void loadBitmap(View view, String uri, XImageSetter imageSetter, XImageLoadCallback callback);

    public void loadBitmap(View view, String uri, XImageOptions options);

    public void configImageOptions(XImageOptions options);

}
