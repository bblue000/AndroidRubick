package androidrubick.xframework.app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidrubick.utils.Preconditions;
import androidrubick.xframework.app.XGlobals;
import androidrubick.xframework.app.ui.internal.XActivityCtrl;

/**
 * 工具类：提供启动Activity的相关的静态调用方法。
 *
 * <p/>
 *
 * 对外提供的方法有：
 * <ul>
 *     <li>在任意处调用startActivity（{@link #startActivity(android.content.Intent)} 和 {@link #startActivity(Class)}）</li>
 *     <li>直接通过Activity类启动Activity（{@link #startActivity(Class)}）</li>
 *     <li>注册/注销全局的Activity操作回调/监听器</li>
 *     <li>判断应用是否在前台</li>
 *     <li>获取应用顶部的（最近打开的）Activity</li>
 * </ul>
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/10 0010.
 */
public class XActivityController {

    private XActivityController() { /* no instance needed */ }

    /**
     * Launch a new activity
     */
    public static void startActivity(Intent intent) {
        Preconditions.checkNotNull(intent, "intent should not be null");
        Context context = XGlobals.getAppContext();
        // TODO 实际上是不是new task不单单是靠这个Flag决定的
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * Launch a new activity of target {@code clz}
     */
    public static void startActivity(Class<? extends Activity> clz) {
        Preconditions.checkNotNull(clz, "clz should not be null");
        Context context = XGlobals.getAppContext();
        startActivity(new Intent(context, clz));
    }

    /**
     * 判断应用是否在前台
     */
    public static boolean isForeground() {
        return XActivityCtrl.getInstance().isForeground();
    }

    /**
     * 获取应用顶部的（最后一次打开的Activity）
     */
    public static XBaseActivity getTopActivity() {
        return (XBaseActivity) XActivityCtrl.getInstance().getTopActivity();
    }

    /**
     * 注册{@link XActivityCallback}，外部处理额外的事
     *
     * <p/>
     *
     * 不要忘记使用注销方法。
     *
     * @see #unregisterActivityCallback(XActivityCallback)
     *
     * @see androidrubick.xframework.app.ui.XActivityCallback.SimpleActivityCallback
     */
    public static void registerActivityCallback(XActivityCallback callback) {
        XActivityCtrl.getInstance().registerActivityCallback(callback);
    }

    /**
     * 注销{@link XActivityCallback}
     *
     * @see #registerActivityCallback(XActivityCallback)
     *
     * @see androidrubick.xframework.app.ui.XActivityCallback.SimpleActivityCallback
     */
    public static void unregisterActivityCallback(XActivityCallback callback) {
        XActivityCtrl.getInstance().unregisterActivityCallback(callback);
    }

}