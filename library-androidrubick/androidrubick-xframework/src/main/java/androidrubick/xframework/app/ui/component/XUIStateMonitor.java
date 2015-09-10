package androidrubick.xframework.app.ui.component;

import java.util.WeakHashMap;

import androidrubick.collect.CollectionsCompat;
import androidrubick.collect.MapBuilder;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/10.
 */
public class XUIStateMonitor {

    private XUIStateMonitor() { /* no instance needed */ }

    static final WeakHashMap<XUICallback, Object> sCallbacks = MapBuilder.newWeakHashMap(4).build();

    /**
     * 注册UI状态回调
     */
    public static void registerUICallback(XUICallback callback) {
        sCallbacks.put(callback, XUIStateMonitor.class);
    }

    /**
     * 注销UI状态回调
     */
    public static void unregisterUICallback(XUICallback callback) {
        sCallbacks.remove(callback);
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
