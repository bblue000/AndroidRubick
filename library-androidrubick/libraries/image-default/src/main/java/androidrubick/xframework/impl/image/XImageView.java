package androidrubick.xframework.impl.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidrubick.utils.Objects;
import androidrubick.xframework.impl.image.R;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/21.
 */
public class XImageView extends ImageView {

    // pre load
    private ScaleType mPreLoadScaleType;
    // on loading
    private ScaleType mLoadingScaleType;
    // on load failed
    private ScaleType mLoadFailedScaleType;
    public XImageView(Context context) {
        super(context);
    }

    public XImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *
     */
    public void setImageUrl(String url) {
        Object oldTag = getTag(R.id.imageview_tag_url);
        setTag(R.id.imageview_tag_url, url);
        if (Objects.equals(url, oldTag)) {
            return;
        }
        // to load new image

    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
    }

    protected void showOnPreload() {

        if (!Objects.isNull(mPreLoadScaleType)) {
            setScaleType(mPreLoadScaleType);
        }
    }

    protected void showOnLoading() {

        if (!Objects.isNull(mLoadingScaleType)) {
            setScaleType(mLoadingScaleType);
        }
    }


}
