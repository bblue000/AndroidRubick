package androidrubick.xframework.app.ui.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import java.util.LinkedList;

import androidrubick.collect.CollectionsCompat;
import androidrubick.utils.FrameworkLog;
import androidrubick.xframework.app.ui.IActivityCallback;

/**
 *
 * Activity生命周期相关回调处理的实现类，可以配置和替换
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/9.
 *
 * @since 1.0
 */
public abstract class XActivityCtrl implements IActivityCallback {

    private static final String TAG = XActivityCtrl.class.getSimpleName();

    private static final XActivityCtrl sInstance;
    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            sInstance = new XActivityCtrlImplAfter14();
        } else {
            sInstance = new XActivityCtrlImplPre14();
        }
    }

    /**
     * 获得{@link Activity} 管理生命周期相关操作管理的单例。
     */
    public static XActivityCtrl getInstance() {
        return sInstance;
    }

    // 打开/关闭Activity都会
    protected boolean mHasPendingIntent;
    // 发现onSaveInstanceState不是在系统即将清理内存时调用，所以该值不准确
    // 那么，暂时，只要走onCreate的Activity就增加，走onDestroy就减少
    protected int mActivityCount;
    protected int mShowingCount;
    protected boolean mMyAppInSight;

    private LinkedList<IActivityCallback> mActivityLifecycleCallbacks =
            new LinkedList<IActivityCallback>();

    public void registerActivityCallback(IActivityCallback callback) {
        synchronized (mActivityLifecycleCallbacks) {
            mActivityLifecycleCallbacks.add(callback);
        }
    }

    public void unregisterActivityCallback(IActivityCallback callback) {
        synchronized (mActivityLifecycleCallbacks) {
            mActivityLifecycleCallbacks.remove(callback);
        }
    }

    /**
     * 用户是否可见
     */
    public boolean isMyAppVisible() {
        return mMyAppInSight;
    }

    public void dispatchStartActivityForResult(Intent intent, int requestCode) {
        FrameworkLog.d(TAG, "准备打开activity = " + intent);
        // 记录状态，该状态下为当前APP打开其他应用
        mHasPendingIntent = true;
    }

    public void dispatchFinishActivity(Activity activity) {
        mHasPendingIntent = (mActivityCount > 1);
    }

    public void dispatchOnActivityCreated(Activity activity, Bundle savedInstanceState) {
        onActivityCreated(activity, savedInstanceState);
    }

    public void dispatchOnActivityNewIntent(Activity activity, Intent intent) {
        onActivityNewIntent(activity, intent);
    }

    public void dispatchOnActivityRestarted(Activity activity) {
        onActivityRestarted(activity);
    }

    public void dispatchOnActivityStarted(Activity activity) {
        onActivityStarted(activity);
    }

    public void dispatchOnActivityRestoreInstanceState(Activity activity, Bundle savedInstanceState) {
        onActivityRestoreInstanceState(activity, savedInstanceState);
    }

    public void dispatchOnActivityResumed(Activity activity) {
        onActivityResumed(activity);
    }

    public void dispatchOnActivityPaused(Activity activity) {
        onActivityPaused(activity);
    }

    public void dispatchOnActivityStopped(Activity activity) {
        onActivityStopped(activity);
    }

    public void dispatchOnActivitySaveInstanceState(Activity activity, Bundle outState) {
        onActivitySaveInstanceState(activity, outState);
    }

    public void dispatchOnActivityDestroyed(Activity activity) {
        onActivityDestroyed(activity);
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // true do
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        FrameworkLog.d(TAG, "新activity打开了 = " + activity);
        if (mActivityCount < 1) {
            onEnterForeground();
        }
        mActivityCount++;

        synchronized (mActivityLifecycleCallbacks) {
            if (!CollectionsCompat.isEmpty(mActivityLifecycleCallbacks)) {
                for (IActivityCallback callback : mActivityLifecycleCallbacks) {
                    callback.onActivityCreated(activity, savedInstanceState);
                }
            }
        }
    }

    public void onActivityNewIntent(Activity activity, Intent intent) {
        FrameworkLog.d(TAG, "activity重新打开了 = " + activity);
        synchronized (mActivityLifecycleCallbacks) {
            if (!CollectionsCompat.isEmpty(mActivityLifecycleCallbacks)) {
                for (IActivityCallback callback : mActivityLifecycleCallbacks) {
                    callback.onActivityNewIntent(activity, intent);
                }
            }
        }
    }

    public void onActivityRestarted(Activity activity) {
        FrameworkLog.d(TAG, "onActivityRestarted = " + activity);

        synchronized (mActivityLifecycleCallbacks) {
            if (!CollectionsCompat.isEmpty(mActivityLifecycleCallbacks)) {
                for (IActivityCallback callback : mActivityLifecycleCallbacks) {
                    callback.onActivityRestarted(activity);
                }
            }
        }
    }

    public void onActivityStarted(Activity activity) {
        FrameworkLog.d(TAG, "onActivityStarted = " + activity);

        synchronized (mActivityLifecycleCallbacks) {
            if (!CollectionsCompat.isEmpty(mActivityLifecycleCallbacks)) {
                for (IActivityCallback callback : mActivityLifecycleCallbacks) {
                    callback.onActivityStarted(activity);
                }
            }
        }
    }

    public void onActivityRestoreInstanceState(Activity activity, Bundle savedInstanceState) {
        synchronized (mActivityLifecycleCallbacks) {
            if (!CollectionsCompat.isEmpty(mActivityLifecycleCallbacks)) {
                for (IActivityCallback callback : mActivityLifecycleCallbacks) {
                    callback.onActivityRestoreInstanceState(activity, savedInstanceState);
                }
            }
        }
    }

    public void onActivityResumed(Activity activity) {
        FrameworkLog.d(TAG, "onActivityResumed = " + activity);
        mMyAppInSight = true;
        mShowingCount++;
        if (mHasPendingIntent) {
            FrameworkLog.d(TAG, "还在应用里面");
        } else {
            FrameworkLog.d(TAG, "貌似回到应用了");
            // 只有一个Activity时，则认为第一次进入
            if (mActivityCount <= 1) {
                onEnterForeground();
            }
        }
        mHasPendingIntent = false;

        synchronized (mActivityLifecycleCallbacks) {
            if (!CollectionsCompat.isEmpty(mActivityLifecycleCallbacks)) {
                for (IActivityCallback callback : mActivityLifecycleCallbacks) {
                    callback.onActivityResumed(activity);
                }
            }
        }
    }

    public void onActivityPaused(Activity activity) {
        FrameworkLog.d(TAG, "onActivityPaused = " + activity);
        mMyAppInSight = false;
        mShowingCount--;
        if (mHasPendingIntent) {
            FrameworkLog.d(TAG, "还在应用里面");
        } else {
            FrameworkLog.d(TAG, "貌似跳出应用了");
            onEnterBackground();
        }

        synchronized (mActivityLifecycleCallbacks) {
            if (!CollectionsCompat.isEmpty(mActivityLifecycleCallbacks)) {
                for (IActivityCallback callback : mActivityLifecycleCallbacks) {
                    callback.onActivityPaused(activity);
                }
            }
        }
    }

    public void onActivityStopped(Activity activity) {
        FrameworkLog.d(TAG, "onActivityStopped = " + activity);

        synchronized (mActivityLifecycleCallbacks) {
            if (!CollectionsCompat.isEmpty(mActivityLifecycleCallbacks)) {
                for (IActivityCallback callback : mActivityLifecycleCallbacks) {
                    callback.onActivityStopped(activity);
                }
            }
        }
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        FrameworkLog.d(TAG, "onActivitySaveInstanceState = " + activity);
        // 这里不确定，因为在正常情况下还是会走这一步
//        mActivityCount--;
        FrameworkLog.d(TAG, "onActivitySaveInstanceState mActivityCount = " + mActivityCount);

        synchronized (mActivityLifecycleCallbacks) {
            if (!CollectionsCompat.isEmpty(mActivityLifecycleCallbacks)) {
                for (IActivityCallback callback : mActivityLifecycleCallbacks) {
                    callback.onActivitySaveInstanceState(activity, outState);
                }
            }
        }
    }

    public void onActivityDestroyed(Activity activity) {
        FrameworkLog.d(TAG, "activity关闭了 = " + activity);
        mActivityCount--;
        FrameworkLog.d(TAG, "onActivityDestroyed mActivityCount = " + mActivityCount);

        synchronized (mActivityLifecycleCallbacks) {
            if (!CollectionsCompat.isEmpty(mActivityLifecycleCallbacks)) {
                for (IActivityCallback callback : mActivityLifecycleCallbacks) {
                    callback.onActivityDestroyed(activity);
                }
            }
        }
    }

    @Override
    public void onEnterBackground() {
        FrameworkLog.d(TAG, "貌似跳出应用了");

        synchronized (mActivityLifecycleCallbacks) {
            if (!CollectionsCompat.isEmpty(mActivityLifecycleCallbacks)) {
                for (IActivityCallback callback : mActivityLifecycleCallbacks) {
                    callback.onEnterBackground();
                }
            }
        }
    }

    @Override
    public void onEnterForeground() {
        FrameworkLog.d(TAG, "貌似回到应用了");

        synchronized (mActivityLifecycleCallbacks) {
            if (!CollectionsCompat.isEmpty(mActivityLifecycleCallbacks)) {
                for (IActivityCallback callback : mActivityLifecycleCallbacks) {
                    callback.onEnterForeground();
                }
            }
        }
    }

}
