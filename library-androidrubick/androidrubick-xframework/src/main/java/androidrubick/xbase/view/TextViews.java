package androidrubick.xbase.view;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidrubick.utils.Objects;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/9.
 */
public class TextViews {

    private TextViews() { /* no instance needed */ }

    public static <TV extends TextView>TV setText(TV tv, CharSequence text) {
        if (!Objects.isNull(tv)) {
            if (tv instanceof EditText) {
                setText((EditText) tv, text);
            } else {
                tv.setText(text);
            }
        }
        return tv;
    }

    public static <TV extends TextView>TV setText(TV tv, int resId) {
        if (!Objects.isNull(tv)) {
            return setText(tv, tv.getResources().getText(resId));
        }
        return tv;
    }

    /**
     * 给{@link EditText}设置文本，并将指针移到最后的位置
     */
    public static <ET extends EditText>ET setText(ET et, CharSequence text) {
        if (!Objects.isNull(et)) {
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
    public static <ET extends EditText>ET setText(ET et, int resId) {
        if (!Objects.isNull(et)) {
            return setText(et, et.getResources().getText(resId));
        }
        return et;
    }

}
