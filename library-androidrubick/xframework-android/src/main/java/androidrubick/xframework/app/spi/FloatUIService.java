package androidrubick.xframework.app.spi;

import android.content.Context;

import androidrubick.xbase.aspi.XSpiService;

/**
 * 本框架中用户可以代码动态自定义的UI（包括，显示提示，显示错误信息）
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/29 0029.
 */
public interface FloatUIService extends XSpiService {

    /**
     * 创建提示信息的UI，并返回一个能够操作它的对象
     *
     * @param context 最好提供Activity
     * @param title
     * @param message
     * @return
     */
    public XFloatUIToken buildTip(Context context, CharSequence title, CharSequence message);

    /**
     * 创建错误信息的UI，并返回一个能够操作它的对象
     *
     * @param context 最好提供Activity
     * @param title
     * @param message
     * @return
     */
    public XFloatUIToken buildError(Context context, CharSequence title, CharSequence message);

    /**
     * 创建显示进度信息的UI，并返回一个能够操作它的对象
     *
     * @param context 最好提供Activity
     * @param title
     * @param message
     * @return
     */
    public XProgressUIToken buildProgress(Context context, CharSequence title, CharSequence message);

}