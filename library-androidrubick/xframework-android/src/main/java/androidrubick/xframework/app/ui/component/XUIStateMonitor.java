package androidrubick.xframework.app.ui.component;

import java.util.WeakHashMap;

import androidrubick.collect.CollectionsCompat;

/**
 *
 * 用于注册和注销UI组件声明周期相关回调的工具类
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/10.
 */
public class XUIStateMonitor {

    private XUIStateMonitor() { /* no instance needed */ }

    static WeakHashMap<XUICallback, Object> sCallbacks;

    /**
     * 注册UI状态回调
     */
    public static void registerUICallback(XUICallback callback) {
        checkCallbacks();
        sCallbacks.put(callback, XUIStateMonitor.class);
    }

    /**
     * 注销UI状态回调
     */
    public static void unregisterUICallback(XUICallback callback) {
        if (CollectionsCompat.isEmpty(sCallbacks)) {
            return;
        }
        sCallbacks.remove(callback);
        if (CollectionsCompat.isEmpty(sCallbacks)) {
            sCallbacks = null;
        }
    }

    private static void checkCallbacks() {
        if (null == sCallbacks) {
            synchronized (XUIStateMonitor.class) {
                if (null == sCallbacks) {
                    sCallbacks = new WeakHashMap<XUICallback, Object>(4);
                }
            }
        }
    }

    /**
     * 组件创建时调用
     */
    /*package*/ static void dispatchOnCreated(XUIComponent flow) {
        if (!CollectionsCompat.isEmpty(sCallbacks)) {
            for (XUICallback callback : sCallbacks.keySet()) {
                callback.onCreated(flow);
            }
        }
    }

    /**
     * 当组件准备加载页面视图时调用
     */
    /*package*/ static void dispatchOnPrepareCreateView(XUIComponent flow) {
        if (!CollectionsCompat.isEmpty(sCallbacks)) {
            for (XUICallback callback : sCallbacks.keySet()) {
                callback.onPrepareCreateView(flow);
            }
        }
    }

    /**
     * 当组件界面加载完成时调用
     */
    /*package*/ static void dispatchOnPostCreatedView(XUIComponent flow) {
        if (!CollectionsCompat.isEmpty(sCallbacks)) {
            for (XUICallback callback : sCallbacks.keySet()) {
                callback.onPostCreatedView(flow);
            }
        }
    }

    /**
     * 准备进行UI，监听器等的初始化操作
     */
    /*package*/ static void dispatchOnPrepareInit(XUIComponent flow) {
        if (!CollectionsCompat.isEmpty(sCallbacks)) {
            for (XUICallback callback : sCallbacks.keySet()) {
                callback.onPrepareInit(flow);
            }
        }
    }

    /**
     * 已经完成UI，监听器等的初始化操作
     */
    /*package*/ static void dispatchOnPostInit(XUIComponent flow) {
        if (!CollectionsCompat.isEmpty(sCallbacks)) {
            for (XUICallback callback : sCallbacks.keySet()) {
                callback.onPostInit(flow);
            }
        }
    }

    /**
     * 组件不再使用时销毁时调用，如Activity是调用{@link android.app.Activity#onDestroy()}后会回调，
     *
     * fragment在Activity不再使用它的时候（“no longer in use”）调用。
     */
    /*package*/ static void dispatchOnDestroy(XUIComponent flow) {
        if (!CollectionsCompat.isEmpty(sCallbacks)) {
            for (XUICallback callback : sCallbacks.keySet()) {
                callback.onDestroy(flow);
            }
        }
    }
}
