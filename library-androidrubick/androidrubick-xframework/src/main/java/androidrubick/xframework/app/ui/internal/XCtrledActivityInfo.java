package androidrubick.xframework.app.ui.internal;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/3/28 0028.
 */
public class XCtrledActivityInfo {

//    LinkedList<XCtrledActivityInfo> sCtrledActivityInfoStack
    protected WeakReference<Activity> mActRef;
    public XCtrledActivityInfo() {

    }
    /**
     *
     * @return
     */
    public Activity getActivityInstance() {
        return null == mActRef ? null : mActRef.get();
    }

    /*package*/ void setActivityInstance(Activity activity) {
        this.mActRef = new WeakReference<Activity>(activity);
    }



//    public boolean isActivityStarted() ;
}
