package androidrubick.xframework.app.broadcast;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 广播功能基类
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/3/23.
 */
abstract class AbsAndroidBroadcast {

    /**
     * 注册广播，参数<code> actions </code> 为注册的行为
     */
    public abstract void registerReceiver(BroadcastReceiver receiver, String...actions);

    /**
     * 注册广播，参数<code> filters </code> 为注册的行为
     */
    public abstract void registerReceiver(BroadcastReceiver receiver, IntentFilter...filters);

    /**
     * 注销指定广播
     */
    public abstract void unregisterReceiver(BroadcastReceiver receiver) ;

    /**
     * 发送指定广播
     */
    public abstract void sendBroadcast(String...actions) ;

    /**
     * 发送指定广播
     */
    public abstract void sendBroadcast(Intent...intents) ;

}
