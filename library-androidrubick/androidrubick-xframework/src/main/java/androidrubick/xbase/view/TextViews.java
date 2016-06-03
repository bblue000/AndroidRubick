package androidrubick.xbase.view;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

/**
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/9.
 */
public class TextViews {

    private TextViews() { /* no instance needed */ }

    public static <TV extends TextView>TV text(TV tv, CharSequence text) {
        if (null != tv) {
            tv.setText(text);
        }
        return tv;
    }

    public static <TV extends TextView>TV text(TV tv, int resId) {
        if (null != tv) {
            return text(tv, tv.getResources().getText(resId));
        }
        return tv;
    }

    /**
     * 给{@link EditText}设置文本，并将指针移到最后的位置
     */
    public static <ET extends EditText>ET text(ET et, CharSequence text) {
        if (null != et) {
            et.setText(text);
            if (!TextUtils.isEmpty(et.getText())) {
                et.setSelection(et.getText().length());
            }
        }
        return et;
    }

    /**
     * 给{@link EditText}设置文本，并将指针移到最后的位置
     */
    public static <ET extends EditText>ET text(ET et, int resId) {
        if (null != et) {
            return text(et, et.getResources().getText(resId));
        }
        return et;
    }

}
