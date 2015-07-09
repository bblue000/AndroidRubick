package androidrubick.xframework.events.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import org.androidrubick.utils.AndroidUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidrubick.utils.FrameworkLog;
import androidrubick.utils.Objects;
import androidrubick.xframework.events.IEventAPI;

/* package */ class XEventBus implements IEventAPI<Object, Object, EventSubscriber>, SubscriptionMonitor,
Runnable {

    private static final String TAG = XEventBus.class.getSimpleName();
    private static final boolean DEBUG = true;

    private static final int DEFAULT_SIZE = 3;
    static final int MSG_EXEC_PENDING_BROADCASTS = 1;

    private static final Object mLock = new Object();
    private static XEventBus mInstance;
    public static XEventBus getInstance() {
        synchronized (mLock) {
            if (mInstance == null) {
                mInstance = new XEventBus();
            }
            return mInstance;
        }
    }

    private final HashMap<EventSubscriber, List<Object>> mReceivers
            = new HashMap<EventSubscriber, List<Object>>();
    private final HashMap<Object, List<EventObserverRecord>> mActions
            = new HashMap<Object, List<EventObserverRecord>>();
    private final ArrayList<EventPosterRecord> mPendingBroadcasts
            = new ArrayList<EventPosterRecord>();

    private final Handler mHandler;
    private XEventBus() {
        EventSubscriber.setSubscriptionMonitor(this);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_EXEC_PENDING_BROADCASTS:
                        run();
                        break;
                    default:
                        super.handleMessage(msg);
                }
            }
        };
    }
    private void printState() {
        if (!DEBUG) {
            return;
        }
        FrameworkLog.d(TAG, "-----XEvent state start----");
        FrameworkLog.d(TAG, "action's number : " + mActions.size());
        FrameworkLog.d(TAG, "receiver's number : " + mReceivers.size());
        FrameworkLog.d(TAG, "-----XEvent state end------");
    }

    /**
     * 注册事件，当<code>actions</code>中的任意事件触发时，subscription将会调用目标方法
     *
     * @param eventSubscriber 触发执行器
     * @param actions 事件
     */
    @Override
    public void register(EventSubscriber eventSubscriber, Object...actions) {
        EventSubscriber.checkRelease();
        if (Objects.isNull(eventSubscriber) || Objects.isEmpty(actions)) {
            return;
        }
        synchronized (mReceivers) {
            List<Object> cachedActions = mReceivers.get(eventSubscriber);
            if (Objects.isNull(cachedActions)) {
                cachedActions = new ArrayList<Object>(DEFAULT_SIZE);
                mReceivers.put(eventSubscriber, cachedActions);
            }
            for (Object action : actions) {
                cachedActions.add(action);
                List<EventObserverRecord> entries = mActions.get(action);
                if (Objects.isNull(entries)) {
                    entries = new ArrayList<EventObserverRecord>(DEFAULT_SIZE);
                    mActions.put(action, entries);
                }
                entries.add(new EventObserverRecord(action, eventSubscriber));
            }
        }
        printState();
    }

    /**
     * 注销事件，当<code>actions</code>为空时，所有subscription注册的事件都会注销；
     * 不为空时，注销指定的事件
     *
     * @param eventSubscriber 触发执行器
     * @param actions 事件
     */
    @Override
    public void unregister(EventSubscriber eventSubscriber, Object...actions) {
        EventSubscriber.checkRelease();
        synchronized (mReceivers) {
            List<Object> unregisterActions;
            if (Objects.isEmpty(actions)) {
                unregisterActions = mReceivers.remove(eventSubscriber);
                if (Objects.isNull(unregisterActions)) {
                    return ;
                }
                for (int i = 0; i < unregisterActions.size(); i++) {
                    Object action = unregisterActions.get(i);
                    unregisterAction(eventSubscriber, action);
                }
            } else {
                List<Object> cachedActions = mReceivers.get(eventSubscriber);
                if (Objects.isNull(cachedActions)) {
                    return ;
                }
                for (Object action : actions) {
                    if (cachedActions.remove(action)) {
                        unregisterAction(eventSubscriber, action);
                    }
                }
            }
        }
        printState();
    }

    private void unregisterAction(EventSubscriber eventSubscriber, Object action) {
        List<EventObserverRecord> receivers = mActions.get(action);
        if (Objects.isNull(receivers)) {
            return ;
        }
        for (int k = 0; k < receivers.size(); k++) {
            EventObserverRecord eventObserverRecord = receivers.get(k);
            EventSubscriber temp = eventObserverRecord.eventSubscriber;
            if (Objects.equals(temp, eventSubscriber)) {
                receivers.remove(k);
                k--;
            }
        }
        if (receivers.size() <= 0) {
            mActions.remove(action);
        }
    }

    /**
     * Like {@link #postToMain(Object)}, but if there are any observers for
     * the Intent this function will block and immediately dispatch them before
     * returning.
     */
    @Override
    public void post(Object action) {
        post(action, null);
    }

    @Override
    public void post(Object action, Object data) {
        checkAndPost(action, data, false);
    }

    @Override
    public void postToMain(Object action) {
        postToMain(action, null);
    }

    @Override
    public void postToMain(Object action, Object data) {
        checkAndPost(action, data, true);
    }

    private boolean checkAndPost(Object action, Object data, boolean mainThread) {
        synchronized (mReceivers) {
            List<EventObserverRecord> entries = mActions.get(action);
            if (Objects.isEmpty(entries)) {
                if (DEBUG) FrameworkLog.d(TAG, "Action no listener: " + action);
                return false;
            }
            if (DEBUG) FrameworkLog.d(TAG, "Action list size: " + entries.size());

            List<EventObserverRecord> receivers = null;
            for (int i = 0; i < entries.size(); i++) {
                EventObserverRecord receiver = entries.get(i);
                if (receiver.broadcasting) {
                    if (DEBUG) FrameworkLog.d(TAG, "Filter's target already added");
                    continue;
                }

                if (Objects.equals(action, receiver.action)) {
                    if (Objects.isNull(receivers)) {
                        receivers = new ArrayList<EventObserverRecord>(DEFAULT_SIZE);
                    }
                    receivers.add(receiver);
                    receiver.broadcasting = true;
                }
            }

            if (!Objects.isNull(receivers)) {
                // clear state
                for (int i = 0; i < receivers.size(); i++) {
                    receivers.get(i).broadcasting = false;
                }
                mPendingBroadcasts.add(new EventPosterRecord(action, receivers, data));
                if (mainThread && !AndroidUtils.isMainThread()) {
                    if (!mHandler.hasMessages(MSG_EXEC_PENDING_BROADCASTS)) {
                        mHandler.sendEmptyMessage(MSG_EXEC_PENDING_BROADCASTS);
                    }
                } else {
                    run();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {
            EventPosterRecord[] brs = null;
            synchronized (mReceivers) {
                final int N = mPendingBroadcasts.size();
                if (N <= 0) {
                    return;
                }
                brs = new EventPosterRecord[N];
                mPendingBroadcasts.toArray(brs);
                mPendingBroadcasts.clear();
            }
            for (int i = 0; i < brs.length; i++) {
                EventPosterRecord br = brs[i];
                for (int j = 0; j < br.observers.size(); j++) {
                    EventObserverRecord eventObserverRecord = br.observers.get(j);
                    eventObserverRecord.eventSubscriber.invoke(br.data);
                }
            }
        }
    }

    /**
     * 自动清理
     *
     * @param eventSubscriber
     */
    @Override
    public void onInstanceReleased(EventSubscriber eventSubscriber) {
        FrameworkLog.d(TAG, "released = " + eventSubscriber);
        unregister(eventSubscriber);
    }
}
