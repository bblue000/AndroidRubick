package androidrubick.xframework.app;

import android.app.Activity;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/3/28 0028.
 */
public interface IControlledActivityInfo {

    public Activity getActivityInstance();

    public boolean isActivityStarted() ;
}
