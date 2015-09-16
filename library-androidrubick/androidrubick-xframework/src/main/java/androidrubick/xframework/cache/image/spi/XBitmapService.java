package androidrubick.xframework.cache.image.spi;

import android.view.View;

import androidrubick.xframework.cache.image.XImageSetter;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/16.
 */
public interface XBitmapService {

    public void loadImage(View view, String uri, XImageSetter imageSetter);

}
