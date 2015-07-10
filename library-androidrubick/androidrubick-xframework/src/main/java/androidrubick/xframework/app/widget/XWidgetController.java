package androidrubick.xframework.app.widget;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

/**
 * 基础的View控制器；
 *
 * <p/>
 *
 * 控制View的可见性；
 *
 * <p/>
 *
 * Created by Yin Yong on 15/5/14.
 */
public class XWidgetController<Self extends XWidgetController> {

    /**
     * 创建指定View的控制器
     */
    public static XWidgetController of(View view) {
        return new XWidgetController(view);
    }

    /**
     * 创建指定View中子项的控制器
     */
    public static XWidgetController of(View parent, int id) {
        return new XWidgetController((View) findViewById(parent, id));
    }

    /**
     * 创建指定Activity中子项的控制器
     */
    public static XWidgetController of(Activity activity, int id) {
        return new XWidgetController((View) findViewById(activity, id));
    }

    /**
     * 从父容器中查找View，直接转为指定的声明类型
     */
    public static <T>T findViewById(View parent, int id) {
        return (T) parent.findViewById(id);
    }

    /**
     * 从Activity中查找View，直接转为指定的声明类型
     */
    public static <T>T findViewById(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }

    protected final View mTarget;
    protected XWidgetController(View view) {
        mTarget = view;
    }

    protected Self self() {
        return (Self) this;
    }

    protected View getView() {
        return mTarget;
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>
    // 可见性
    /**
     * 设置View的可视属性
     */
    public Self visibility(int visibility) {
        getView().setVisibility(visibility);
        return self();
    }

    /**
     * 设置View可见
     */
    public Self visible() {
        return visibility(View.VISIBLE);
    }

    /**
     * 设置View不可见，且不预留区域
     */
    public Self gone() {
        return visibility(View.GONE);
    }

    /**
     * 设置View不可见，且不预留区域
     */
    public Self invisible() {
        return visibility(View.INVISIBLE);
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>
    // 监听事件
    /**
     * 设置点击事件
     */
    public Self clicked(View.OnClickListener listener) {
        getView().setOnClickListener(listener);
        return self();
    }

    /**
     * 设置点击事件
     */
    public Self longClicked(View.OnLongClickListener listener) {
        getView().setOnLongClickListener(listener);
        return self();
    }

    /**
     * 设置键盘点击事件
     */
    public Self keyListener(View.OnKeyListener listener) {
        getView().setOnKeyListener(listener);
        return self();
    }

    /**
     * 设置触摸事件
     */
    public Self touch(View.OnTouchListener listener) {
        getView().setOnTouchListener(listener);
        return self();
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>
    // 视图
    /**
     * 刷新，重绘
     */
    public Self refresh() {
        getView().requestLayout();;
        getView().invalidate();
        return self();
    }

    /**
     * 设置背景
     */
    public Self background(Drawable drawable) {
        getView().setBackgroundDrawable(drawable);
        return self();
    }

    /**
     * 设置背景
     */
    public Self background(int drawableResId) {
        getView().setBackgroundResource(drawableResId);
        return self();
    }

    /**
     * 设置背景
     */
    public Self backgroundColor(int color) {
        getView().setBackgroundColor(color);
        return self();
    }

    /**
     * 设置宽度
     */
    public Self width(int width) {
        ViewGroup.LayoutParams lp = getView().getLayoutParams();
        if (null == lp) {
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            getView().setLayoutParams(lp);
        }
        lp.width = width;
        return refresh();
    }

    /**
     * 设置高度
     */
    public Self height(int height) {
        ViewGroup.LayoutParams lp = getView().getLayoutParams();
        if (null == lp) {
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            getView().setLayoutParams(lp);
        }
        lp.height = height;
        return refresh();
    }

    /**
     * 设置宽高
     */
    public Self size(int width, int height) {
        return (Self) width(width).height(height).refresh();
    }

}
