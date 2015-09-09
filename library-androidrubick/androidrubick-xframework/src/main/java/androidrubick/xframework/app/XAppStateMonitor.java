package androidrubick.xframework.app;

import android.content.res.Configuration;

import java.util.WeakHashMap;

import androidrubick.collect.MapBuilder;
import androidrubick.utils.Objects;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/8.
 */
public class XAppStateMonitor {

    static final WeakHashMap<XAppStateCallback, Object> sCallbacks = MapBuilder.newHashMap(4).build();

    /**
     * 注册状态监听器
     */
    public static void registerAppStateCallback(XAppStateCallback callback) {
        sCallbacks.put(callback, XAppStateMonitor.class);
    }

    /**
     * 注销状态监听器
     */
    public static void unregisterAppStateCallback(XAppStateCallback callback) {
        sCallbacks.remove(callback);
    }

    /**
     * @hide
     */
    /*package*/ static void onAppConfigurationChanged(Configuration newConfig) {
        for (XAppStateCallback callback : sCallbacks.keySet()) {
            if (Objects.isNull(callback)) {
                continue;
            }
            callback.onConfigurationChanged(newConfig);
        }
    }

    /**
     * @hide
     */
    /*package*/ static void onAppLowMemory() {
        for (XAppStateCallback callback : sCallbacks.keySet()) {
            if (Objects.isNull(callback)) {
                continue;
            }
            callback.onLowMemory();
        }
    }

    /**
     * @hide
     */
    /*package*/ static void onAppTrimMemory(int level) {
        for (XAppStateCallback callback : sCallbacks.keySet()) {
            if (Objects.isNull(callback)) {
                continue;
            }
            callback.onTrimMemory(level);
        }
    }

    /**
     * 进入后台运行（锁屏暂也属于进入后台，有待改进）
     * @hide
     */
    /*package*/ static void onEnterBackground() {
        for (XAppStateCallback callback : sCallbacks.keySet()) {
            if (Objects.isNull(callback)) {
                continue;
            }
            callback.onEnterBackground();
        }
    }

    /**
     * 回到前台运行（锁屏暂也属于进入后台，有待改进）
     * @hide
     */
    /*package*/ static void onEnterForeground() {
        for (XAppStateCallback callback : sCallbacks.keySet()) {
            if (Objects.isNull(callback)) {
                continue;
            }
            callback.onEnterForeground();
        }
    }

}
