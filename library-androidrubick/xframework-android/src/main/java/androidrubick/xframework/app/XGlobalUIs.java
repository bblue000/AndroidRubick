package androidrubick.xframework.app;

import android.content.Context;

import androidrubick.xbase.aspi.XServiceLoader;
import androidrubick.xframework.app.spi.XFloatUIService;
import androidrubick.xframework.app.spi.XFloatUIToken;
import androidrubick.xframework.app.spi.XProgressUIToken;

/**
 * 与用户交互相关的工具类，将一些可以抽象出来全局控制。
 *
 * <p/>
 *
 * <b>如：</b>提示信息、错误信息的展示，进度、对话框的展示
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/29 0029.
 */
public class XGlobalUIs {

    private XGlobalUIs() { /* no instance needed */ }

    /**
     * 全局通用的显示提示信息的方法
     *
     * @param context a context instance
     * @param message 直接的文本字符串
     * @return tokens 应对可能需要的扩展
     */
    public static XFloatUIToken showTip(Context context, CharSequence title, CharSequence message) {
        XFloatUIToken token = XServiceLoader.load(XFloatUIService.class).buildTip(context, title, message);
        token.show();
        return token;
    }

    /**
     * 全局通用的显示错误信息的方法
     *
     * @param context a context instance
     * @param message 直接的文本字符串
     * @return tokens 应对可能需要的扩展
     */
    public static XFloatUIToken showError(Context context, CharSequence title, CharSequence message) {
        XFloatUIToken token = XServiceLoader.load(XFloatUIService.class).buildError(context, title, message);
        token.show();
        return token;
    }

    /**
     * 全局通用的显示进度信息的方法
     *
     * @param context a context instance
     * @param message 直接的文本字符串
     * @return tokens 应对可能需要的扩展
     */
    public static XProgressUIToken showProgress(Context context, CharSequence title, CharSequence message) {
        XProgressUIToken token = XServiceLoader.load(XFloatUIService.class).buildProgress(context, title, message);
        token.show();
        return token;
    }

}