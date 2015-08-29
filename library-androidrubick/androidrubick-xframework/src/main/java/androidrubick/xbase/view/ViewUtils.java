package androidrubick.xbase.view;

import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidrubick.utils.Objects;

/**
 * View或Widget的一些通用操作的工具类
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/29 0029.
 *
 * @since 1.0
 */
public class ViewUtils {

    private ViewUtils() { /*no instance*/ }

    public static void setVisibility(View view, int visibility) {
        if (!Objects.isNull(view)) {
            view.setVisibility(visibility);
        }
    }

    /**
     * 如果<code>show</code>为true，将<code>view</code>设置为{@link View#VISIBLE}；
     *
     * 如果<code>show</code>为false，将<code>view</code>设置为{@link View#GONE}；
     */
    public static void setVisibility(View view, boolean show) {
        int visibility = show ? View.VISIBLE : View.GONE;
        setVisibility(view, visibility);
    }

    public static boolean isVisibilityEqual(View view, int visibility) {
        if (Objects.isNull(view)) {
            return false;
        }
        return view.getVisibility() == visibility;
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

    public static void setVisible(View view) {
        setVisibility(view, View.VISIBLE);
    }

    public static void setInvisible(View view) {
        setVisibility(view, View.INVISIBLE);
    }

    public static void setGone(View view) {
        setVisibility(view, View.GONE);
    }


    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // 获取View的显示区域
    /**
     * <code>view</code>在整个屏幕中的可视区域
     */
    public static boolean getGlobalVisibleRect(View view, Rect rect) {
        if (Objects.isNull(view) || Objects.isNull(rect)) {
            return false;
        }
        return view.getGlobalVisibleRect(rect);
    }

    /**
     * <code>view</code>在父控件中的可视区域
     */
    public static boolean getLocalVisibleRect(View view, Rect rect) {
        if (Objects.isNull(view) || Objects.isNull(rect)) {
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
    public static int maxWidthOfView() {
        return (0x1 << 30) - 1;
    }

    /**
     * View的最大高度；int一共32位，在View中前8位用来标识{@link View#MEASURED_STATE_MASK}
     *
     * 的模式
     *
     * @see View#MEASURED_SIZE_MASK
     * @see View#MEASURED_STATE_MASK
     */
    public static int maxHeightOfView() {
        return (0x1 << 30) - 1;
    }

    public static void setText(TextView tv, CharSequence text) {
        if (!Objects.isNull(tv)) {
            if (tv instanceof EditText) {
                setText((EditText) tv, text);
            } else {
                tv.setText(text);
            }
        }
    }

    public static void setText(TextView tv, int resId) {
        if (!Objects.isNull(tv)) {
            setText(tv, tv.getResources().getText(resId));
        }
    }

    /**
     * 给{@link EditText}设置文本，并将指针移到最后的位置
     */
    public static void setText(EditText et, CharSequence text) {
        if (!Objects.isNull(et)) {
            et.setText(text);
            if (!TextUtils.isEmpty(et.getText())) {
                et.setSelection(et.getText().length());
            }
        }
    }

    /**
     * 给{@link EditText}设置文本，并将指针移到最后的位置
     */
    public static void setText(EditText et, int resId) {
        if (!Objects.isNull(et)) {
            setText(et, et.getResources().getText(resId));
        }
    }

}
