package org.androidrubick.app;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import androidrubick.utils.Objects;

/**
 * 本地广播的封装
 * 
 * @author Yin Yong
 *
 */
public class LocalBroadcasts {

    private LocalBroadcasts() {}

    private static LocalBroadcastManager sLocalBroadcastManager;
    private synchronized static void checkLocalBroadcastManagerInstance() {
        if (null == sLocalBroadcastManager) {
            sLocalBroadcastManager = LocalBroadcastManager.getInstance(BaseApplication.getAppContext());
        }
    }

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
        checkLocalBroadcastManagerInstance();
        sLocalBroadcastManager.registerReceiver(receiver, filter);
    };

    /**
     * 注销指定广播
     */
    public static void unregisterReceiver(BroadcastReceiver receiver) {
        checkLocalBroadcastManagerInstance();
        try {
            sLocalBroadcastManager.unregisterReceiver(receiver);
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
        checkLocalBroadcastManagerInstance();
        sLocalBroadcastManager.sendBroadcast(intent);
    };
	
}
