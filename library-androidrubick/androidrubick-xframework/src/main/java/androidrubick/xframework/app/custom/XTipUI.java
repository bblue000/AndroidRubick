package androidrubick.xframework.app.custom;

import android.content.Context;

import org.androidrubick.utils.ToastUtils;

import androidrubick.xframework.xbase.annotation.Configurable;

/**
 *
 * <p/>
 *
 * Created by Yin Yong on 15/5/14.
 */
public class XTipUI {

    public static XTipUI from(Context context) {
        return new XTipUI(context);
    }

    protected Context mContext;
    protected CharSequence mMessage;
    protected Object[] mTokens;
    protected XTipUI(Context context) {
        mContext = context;
    }

    public XTipUI message(int resId) {
        return message(mContext.getString(resId));
    }

    public XTipUI message(CharSequence message) {
        mMessage = message;
        return this;
    }

    public XTipUI tokens(Object...tokens) {
        mTokens = tokens;
        return this;
    }

    /**
     * 显示提示
     */
    @Configurable
    public void show() {
        ToastUtils.showToast(mMessage);
    }

}
