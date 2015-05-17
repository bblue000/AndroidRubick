package androidrubick.xframework.app.custom;

import android.content.Context;

import org.androidrubick.utils.ToastUtils;

/**
 *
 *
 * <p/>
 *
 * Created by Yin Yong on 15/5/14.
 */
public class XErrorUI {

    public static XErrorUI from(Context context) {
        return new XErrorUI(context);
    }

    protected Context mContext;
    protected CharSequence mMessage;
    protected Object[] mTokens;
    protected XErrorUI(Context context) {
        mContext = context;
    }

    public XErrorUI message(int resId) {
        return message(mContext.getString(resId));
    }

    public XErrorUI message(CharSequence message) {
        mMessage = message;
        return this;
    }

    public XErrorUI tokens(Object...tokens) {
        mTokens = tokens;
        return this;
    }

    /**
     * 显示错误提示
     */
    public void show() {
        ToastUtils.showToast(mMessage);
    }

}
