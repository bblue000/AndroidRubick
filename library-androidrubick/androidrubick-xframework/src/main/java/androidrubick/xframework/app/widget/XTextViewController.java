package androidrubick.xframework.app.widget;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

/**
 *
 *
 * <p/>
 *
 * Created by Yin Yong on 15/5/14.
 */
public class XTextViewController extends XWidgetController<XTextViewController> {

    /**
     * 创建指定View的控制器
     */
    public static XTextViewController of(TextView view) {
        return new XTextViewController(view);
    }

    /**
     * 创建指定View中子项的控制器
     */
    public static XTextViewController of(View parent, int id) {
        return new XTextViewController((TextView) findViewById(parent, id));
    }

    /**
     * 创建指定Activity中子项的控制器
     */
    public static XTextViewController of(Activity activity, int id) {
        return new XTextViewController((TextView) findViewById(activity, id));
    }

    protected XTextViewController(TextView view) {
        super(view);
    }

    @Override
    protected TextView getView() {
        return (TextView) super.getView();
    }

    /**
     * 设置文本
     */
    public XTextViewController text(int resId) {
        getView().setText(resId);
        return self();
    }

    /**
     * 设置文本
     */
    public XTextViewController text(CharSequence text) {
        getView().setText(text);
        return self();
    }

    /**
     * 设置字体大小
     */
    public XTextViewController textSize(float size) {
        getView().setTextSize(size);
        return self();
    }

    /**
     * 设置字体大小
     */
    public XTextViewController textSize(int unit, float size) {
        getView().setTextSize(unit, size);
        return self();
    }

    /**
     * 设置字体颜色
     */
    public XTextViewController textColor(int color) {
        getView().setTextColor(color);
        return self();
    }

    /**
     * 设置字体颜色
     */
    public XTextViewController textColorRes(int resId) {
        getView().setTextColor(getView().getResources().getColorStateList(resId));
        return self();
    }
}
