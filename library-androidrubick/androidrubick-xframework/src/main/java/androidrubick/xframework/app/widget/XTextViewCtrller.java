package androidrubick.xframework.app.widget;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidrubick.xbase.view.ViewUtils;

/**
 *
 *
 * <p/>
 *
 * Created by Yin Yong on 15/5/14.
 */
public class XTextViewCtrller extends XWidgetCtrller<XTextViewCtrller> {

    /**
     * 创建指定View的控制器
     */
    public static XTextViewCtrller of(TextView view) {
        return new XTextViewCtrller(view);
    }

    /**
     * 创建指定View中子项的控制器
     */
    public static XTextViewCtrller of(View parent, int id) {
        return new XTextViewCtrller((TextView) findViewById(parent, id));
    }

    /**
     * 创建指定Activity中子项的控制器
     */
    public static XTextViewCtrller of(Activity activity, int id) {
        return new XTextViewCtrller((TextView) findViewById(activity, id));
    }

    protected XTextViewCtrller(TextView view) {
        super(view);
    }

    @Override
    protected TextView getView() {
        return (TextView) super.getView();
    }

    /**
     * 设置文本
     */
    public XTextViewCtrller text(int resId) {
        ViewUtils.setText(getView(), resId);
        return self();
    }

    /**
     * 设置文本
     */
    public XTextViewCtrller text(CharSequence text) {
        ViewUtils.setText(getView(), text);
        return self();
    }

    /**
     * 设置字体大小
     */
    public XTextViewCtrller textSize(float size) {
        getView().setTextSize(size);
        return self();
    }

    /**
     * 设置字体大小
     */
    public XTextViewCtrller textSize(int unit, float size) {
        getView().setTextSize(unit, size);
        return self();
    }

    /**
     * 设置字体颜色
     */
    public XTextViewCtrller textColor(int color) {
        getView().setTextColor(color);
        return self();
    }

    /**
     * 设置字体颜色
     */
    public XTextViewCtrller textColorRes(int resId) {
        getView().setTextColor(getView().getResources().getColorStateList(resId));
        return self();
    }
}
