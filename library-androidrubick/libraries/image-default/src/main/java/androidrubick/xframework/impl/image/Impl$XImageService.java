package androidrubick.xframework.impl.image;

import android.view.View;

import androidrubick.xframework.cache.image.XImageLoadCallback;
import androidrubick.xframework.cache.image.XImageOptions;
import androidrubick.xframework.cache.image.XImageSetter;
import androidrubick.xframework.cache.image.spi.XImageService;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/22.
 */
public class Impl$XImageService implements XImageService {

    @Override
    public void loadBitmap(String uri, XImageLoadCallback callback) {

    }

    @Override
    public void loadBitmap(View view, String uri, XImageSetter imageSetter, XImageLoadCallback callback) {

    }

    @Override
    public void loadBitmap(View view, String uri, XImageOptions options) {

    }

    @Override
    public void configImageOptions(XImageOptions options) {

    }

    @Override
    public void trimMemory() {

    }

    @Override
    public boolean multiInstance() {
        return false;
    }

}
