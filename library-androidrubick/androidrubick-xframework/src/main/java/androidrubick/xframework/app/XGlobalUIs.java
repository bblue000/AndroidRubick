package androidrubick.xframework.app;

import android.content.Context;

import androidrubick.xframework.app.custom.XErrorUI;
import androidrubick.xframework.app.custom.XTipUI;

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
     * @param resId string resouce id
     * @param tokens other tokens 应对可能需要的扩展
     */
    public static void showTip(Context context, int resId, Object...tokens) {
        XTipUI.from(context).message(resId).tokens(tokens).show();
    }

    /**
     * 全局通用的显示提示信息的方法
     *
     * @param context a context instance
     * @param message 直接的文本字符串
     * @param tokens other tokens 应对可能需要的扩展
     */
    public static void showTip(Context context, CharSequence message, Object...tokens) {
        XTipUI.from(context).message(message).tokens(tokens).show();
    }

    /**
     * 全局通用的错误提示信息的方法
     *
     * @param context a context instance
     * @param resId string resouce id
     * @param tokens other tokens 应对可能需要的扩展
     */
    public static void showError(Context context, int resId, Object...tokens) {
        XErrorUI.from(context).message(resId).tokens(tokens).show();
    }

    /**
     * 全局通用的显示错误信息的方法
     *
     * @param context a context instance
     * @param message 直接的文本字符串
     * @param tokens other tokens 应对可能需要的扩展
     */
    public static void showError(Context context, CharSequence message, Object...tokens) {
        XErrorUI.from(context).message(message).tokens(tokens).show();
    }

}