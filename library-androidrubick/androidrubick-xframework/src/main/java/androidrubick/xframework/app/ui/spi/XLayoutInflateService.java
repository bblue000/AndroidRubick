package androidrubick.xframework.app.ui.spi;

import android.view.View;
import android.view.ViewGroup;

import androidrubick.xframework.app.ui.XBaseActivity;
import androidrubick.xframework.app.ui.XBaseFragment;

/**
 *
 * 根据{@link androidrubick.xframework.app.ui.XBaseActivity}
 * 或{@link androidrubick.xframework.app.ui.XBaseFragment}中提供的
 *
 * <code>layoutResId</code>或者<code>contentView</code>
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/9.
 */
public interface XLayoutInflateService {

    public View inflateRootView(XBaseActivity activity,
                                View contentView, int layoutResId);

    public View inflateRootView(XBaseFragment activity, ViewGroup parent,
                                View contentView, int layoutResId);

}
