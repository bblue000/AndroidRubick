package androidrubick.xframework.view;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * App对三方组件封装一层，以便后续有统一的操作
 *
 *
 * Created by Yin Yong on 16/3/18.
 */
public class XImageView extends SimpleDraweeView {

    public XImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public XImageView(Context context) {
        super(context);
    }

    public XImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public XImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
