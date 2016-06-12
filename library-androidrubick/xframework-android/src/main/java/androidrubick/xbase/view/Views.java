package androidrubick.xbase.view;

import android.graphics.Rect;
import android.view.View;

/**
 * View或Widget的一些通用操作的工具类
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/29 0029.
 *
 * @since 1.0
 */
public class Views {

    private Views() { /*no instance*/ }

    // visibility
    public static <V extends View>V visible(V view, int visibility) {
        return visibility(view, View.VISIBLE);
    }

    public static <V extends View>V invisible(V view, int visibility) {
        return visibility(view, View.INVISIBLE);
    }

    public static <V extends View>V gone(V view, int visibility) {
        return visibility(view, View.GONE);
    }

    /**
     * 如果<code>show</code>为true，将<code>view</code>设置为{@link View#VISIBLE}；
     *
     * 如果<code>show</code>为false，将<code>view</code>设置为{@link View#GONE}；
     */
    public static <V extends View>V visibility(V view, boolean show) {
        return visibility(view, show ? View.VISIBLE : View.GONE);
    }

    private static <V extends View>V visibility(V view, int visibility) {
        if (null != view) {
            view.setVisibility(visibility);
        }
        return view;
    }

    public static boolean isVisible(View view) {
        return isVisibilityEqual(view, View.VISIBLE);
    }

    public static boolean isInvisible(View view) {
        return isVisibilityEqual(view, View.INVISIBLE);
    }

    public static boolean isGone(View view) {
        return isVisibilityEqual(view, View.GONE);
    }

    private static boolean isVisibilityEqual(View view, int visibility) {
        if (null == view) {
            return false;
        }
        return view.getVisibility() == visibility;
    }

    public static <V extends View>V clicked(V view, View.OnClickListener listener) {
        if (null != view) {
            view.setOnClickListener(listener);
        }
        return view;
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // 获取View的显示区域
    /**
     * <code>view</code>在整个屏幕中的可视区域
     */
    public static boolean getGlobalVisibleRect(View view, Rect rect) {
        if (null == view || null == rect) {
            return false;
        }
        return view.getGlobalVisibleRect(rect);
    }

    /**
     * <code>view</code>在父控件中的可视区域
     */
    public static boolean getLocalVisibleRect(View view, Rect rect) {
        if (null == view || null == rect) {
            return false;
        }
        return view.getLocalVisibleRect(rect);
    }

    // 对于ScrollView不可行
//    /**
//     * 获取<code>view</code>在整个窗口中的位置，不仅仅是可见区域
//     */
//    public static boolean getGlobalFullRect(View view, Rect rect) {
//        if (!getLocalFullRect(view, rect)) {
//            return false;
//        }
//        int offsetLeft = Math.min(0, rect.left - 0);
//        int offsetTop = Math.min(0, rect.top - 0);
//        int childRight = rect.right;
//        int childBottom = rect.bottom;
//        // 获取父控件，如果没有父控件，就直接返回上面的rect
//        ViewGroup parent = (ViewGroup) view.getParent();
//        if (Objects.isNull(parent)) {
//            return true;
//        }
//        parent.getGlobalVisibleRect(rect);
//        int parentWidth = Math.max(0, rect.width());
//        int parentHeight = Math.max(0, rect.height());
//        int offsetRight = Math.max(0, childRight - parentWidth);
//        int offsetBottom = Math.max(0, childBottom - parentHeight);
//
//        view.getGlobalVisibleRect(rect);
//        rect.left += offsetLeft;
//        rect.top += offsetTop;
//        rect.right += offsetRight;
//        rect.bottom += offsetBottom;
//        return true;
//    }
//
//    /**
//     * 获取<code>view</code>在父控件中的位置，不仅仅是可见区域
//     */
//    public static boolean getLocalFullRect(View view, Rect rect) {
//        if (Objects.isNull(view) || Objects.isNull(rect)) {
//            return false;
//        }
//        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
//        return true;
//    }

    /**
     * View的最大宽度；int一共32位，在View中前8位用来标识{@link View#MEASURED_STATE_MASK}
     *
     * 的模式
     *
     * @see View#MEASURED_SIZE_MASK
     * @see View#MEASURED_STATE_MASK
     */
    public static int maxWidth() {
        return View.MEASURED_SIZE_MASK;
    }

    /**
     * View的最大高度；int一共32位，在View中前8位用来标识{@link View#MEASURED_STATE_MASK}
     *
     * 的模式
     *
     * @see View#MEASURED_SIZE_MASK
     * @see View#MEASURED_STATE_MASK
     */
    public static int maxHeight() {
        return View.MEASURED_SIZE_MASK;
    }

}
