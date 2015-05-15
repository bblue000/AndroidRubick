package androidrubick.xframework.app.view;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

/**
 *
 *
 * <p/>
 *
 * Created by Yin Yong on 15/5/14.
 */
public class XEditTextController extends XViewController<XEditTextController> {

    /**
     * 创建指定View的控制器
     */
    public static XEditTextController of(EditText view) {
        return new XEditTextController(view);
    }

    /**
     * 创建指定View中子项的控制器
     */
    public static XEditTextController of(View parent, int id) {
        return new XEditTextController((EditText) findViewById(parent, id));
    }

    /**
     * 创建指定Activity中子项的控制器
     */
    public static XEditTextController of(Activity activity, int id) {
        return new XEditTextController((EditText) findViewById(activity, id));
    }

    protected XEditTextController(EditText view) {
        super(view);
    }

    @Override
    protected EditText getView() {
        return (EditText) super.getView();
    }

    /**
     * 设置文本
     */
    public XEditTextController text(int resId) {
        getView().setText(resId);
        getView().setSelection(getView().getText().length());
        return self();
    }

    /**
     * 设置文本
     */
    public XEditTextController text(CharSequence text) {
        getView().setText(text);
        getView().setSelection(getView().getText().length());
        return self();
    }

}
