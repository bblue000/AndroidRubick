package androidrubick.xframework.app.widget;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import androidrubick.xbase.view.ViewUtils;

/**
 *
 *
 * <p/>
 *
 * Created by Yin Yong on 15/5/14.
 */
public class XEditTextCtrller extends XTextViewCtrller {

    /**
     * 创建指定View的控制器
     */
    public static XEditTextCtrller of(EditText view) {
        return new XEditTextCtrller(view);
    }

    /**
     * 创建指定View中子项的控制器
     */
    public static XEditTextCtrller of(View parent, int id) {
        return new XEditTextCtrller((EditText) findViewById(parent, id));
    }

    /**
     * 创建指定Activity中子项的控制器
     */
    public static XEditTextCtrller of(Activity activity, int id) {
        return new XEditTextCtrller((EditText) findViewById(activity, id));
    }

    protected XEditTextCtrller(EditText view) {
        super(view);
    }

    @Override
    protected EditText getView() {
        return (EditText) super.getView();
    }

    @Override
    protected XEditTextCtrller self() {
        return (XEditTextCtrller) super.self();
    }

    /**
     * 设置文本
     */
    @Override
    public XEditTextCtrller text(int resId) {
        ViewUtils.setText(getView(), resId);
        return self();
    }

    /**
     * 设置文本
     */
    @Override
    public XEditTextCtrller text(CharSequence text) {
        ViewUtils.setText(getView(), text);
        return self();
    }

}
