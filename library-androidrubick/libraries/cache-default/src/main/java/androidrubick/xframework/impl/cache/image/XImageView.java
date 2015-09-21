package androidrubick.xframework.impl.cache.image;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidrubick.utils.Objects;
import androidrubick.xframework.impl.cache.R;

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
        setTag(R.id.imageview_tag_url, url);
        // to load
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
