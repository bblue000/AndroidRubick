package org.androidrubick.app;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

import androidrubick.utils.Objects;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/3/23.
 */
public class Broadcasts {

    private Broadcasts() {}

    /**
     * 注册广播，参数<code> actions </code> 为注册的行为
     */
    public static void registerReceiver(BroadcastReceiver receiver, String...actions) {
        if (Objects.isNull(receiver) || Objects.isEmpty(actions)) {
            return ;
        }
        IntentFilter filter = new IntentFilter();
        for (int i = 0; i < actions.length; i++) {
            filter.addAction(actions[i]);
        }
        registerReceiver(receiver, filter);
    };

    /**
     * 注册广播，参数<code> filters </code> 为注册的行为
     */
    public static void registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        if (Objects.isNull(receiver) || Objects.isNull(filter)) {
            return ;
        }
        BaseApplication.getAppContext().registerReceiver(receiver, filter);
    };

    /**
     * 注销指定广播
     */
    public static void unregisterReceiver(BroadcastReceiver receiver) {
        try {
            BaseApplication.getAppContext().unregisterReceiver(receiver);
        } catch (Exception e) { }
    };

    /**
     * 发送指定广播
     */
    public static void sendBroadcast(String action) {
        sendBroadcast(new Intent(action));
    };

    /**
     * 发送指定广播
     */
    public static void sendBroadcast(Intent intent) {
        if (Objects.isNull(intent)) {
            return ;
        }
        BaseApplication.getAppContext().sendBroadcast(intent);
    };

}
