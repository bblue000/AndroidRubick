package androidrubick.xframework.floatui;

import android.content.Context;

import androidrubick.xbase.util.ToastUtils;
import androidrubick.xframework.app.spi.FloatUIService;
import androidrubick.xframework.app.spi.XFloatUIToken;
import androidrubick.xframework.app.spi.XProgressUIToken;

/**
 * Created by Yin Yong on 16/6/7.
 */
public class Impl$FloatUIService implements FloatUIService {
    @Override
    public XFloatUIToken buildTip(Context context, CharSequence title, final CharSequence message) {
        return new XFloatUIToken() {
            @Override
            public void dismiss() {

            }

            @Override
            public void show() {
                ToastUtils.showToast(message);
            }
        };
    }

    @Override
    public XFloatUIToken buildError(Context context, CharSequence title, final CharSequence message) {
        return new XFloatUIToken() {
            @Override
            public void dismiss() {

            }

            @Override
            public void show() {
                ToastUtils.showToast(message);
            }
        };
    }

    @Override
    public XProgressUIToken buildProgress(Context context, CharSequence title, CharSequence message) {
        return new XProgressUIToken() {
            @Override
            public void updateProgress(int percent, float fraction) {

            }

            @Override
            public void dismiss() {

            }

            @Override
            public void show() {

            }
        };
    }

    @Override
    public void trimMemory() {

    }

    @Override
    public boolean multiInstance() {
        return false;
    }
}
