package androidrubick.xframework.events.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidrubick.utils.FrameworkLog;
import androidrubick.utils.Objects;

/* package */ class XEventBus {

    private static final String TAG = XEventBus.class.getSimpleName();
    private static final boolean DEBUG = false;

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

    private final HashMap<Subscription, List<Object>> mReceivers
            = new HashMap<Subscription, List<Object>>();
    private final HashMap<Object, List<EventObserverRecord>> mActions
            = new HashMap<Object, List<EventObserverRecord>>();
    private final ArrayList<EventPosterRecord> mPendingBroadcasts
            = new ArrayList<EventPosterRecord>();
    private XEventBus() {
    }

    /**
     * 注册事件，当<code>actions</code>中的任意事件触发时，subscription将会调用目标方法
     *
     * @param subscription 触发执行器
     * @param actions 事件
     */
    public void register(Subscription subscription, Object...actions) {
        if (Objects.isNull(subscription) || Objects.isEmpty(actions)) {
            return;
        }
        synchronized (mReceivers) {
            List<Object> cachedActions = mReceivers.get(subscription);
            if (Objects.isNull(cachedActions)) {
                cachedActions = new ArrayList<Object>(DEFAULT_SIZE);
                mReceivers.put(subscription, cachedActions);
            }
            for (Object action : actions) {
                cachedActions.add(action);
                List<EventObserverRecord> entries = mActions.get(action);
                if (entries == null) {
                    entries = new ArrayList<EventObserverRecord>(DEFAULT_SIZE);
                    mActions.put(action, entries);
                }
                entries.add(new EventObserverRecord(action, subscription));
            }
        }
    }

    /**
     * 注销事件，当<code>actions</code>为空时，所有subscription注册的事件都会注销；
     * 不为空时，注销指定的事件
     *
     * @param subscription 触发执行器
     * @param actions 事件
     */
    public void unregister(Subscription subscription, Object...actions) {
        synchronized (mReceivers) {
            List<Object> unregisterActions;
            if (Objects.isEmpty(actions)) {
                unregisterActions = mReceivers.remove(subscription);
                if (Objects.isNull(unregisterActions)) {
                    return ;
                }
                for (int i = 0; i < unregisterActions.size(); i++) {
                    Object action = unregisterActions.get(i);
                    unregisterAction(subscription, action);
                }
            } else {
                List<Object> cachedActions = mReceivers.get(subscription);
                if (Objects.isNull(cachedActions)) {
                    return ;
                }
                for (Object action : actions) {
                    if (cachedActions.remove(action)) {
                        unregisterAction(subscription, action);
                    }
                }
            }
        }
    }

    private void unregisterAction(Subscription subscription, Object action) {
        List<EventObserverRecord> receivers = mActions.get(action);
        if (Objects.isNull(receivers)) {
            return ;
        }
        for (int k = 0; k < receivers.size(); k++) {
            EventObserverRecord eventObserverRecord = receivers.get(k);
            Subscription temp = eventObserverRecord.subscription;
            if (Objects.equals(temp, subscription)) {
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
    public void post(Object action) {
        post(action, null);
    }

    public void post(Object action, Object data) {
        synchronized (mReceivers) {
            List<EventObserverRecord> entries = mActions.get(action);
            if (Objects.isEmpty(entries)) {
                return ;
            }
            if (DEBUG) FrameworkLog.d(TAG, "Action list: " + entries);

            List<EventObserverRecord> receivers = null;
            for (int i = 0; i < entries.size(); i++) {
                EventObserverRecord receiver = entries.get(i);
                if (DEBUG) FrameworkLog.d(TAG, "Matching against filter action " + receiver.action);

                if (receiver.broadcasting) {
                    if (DEBUG) {
                        FrameworkLog.d(TAG, "  Filter's target already added");
                    }
                    continue;
                }

                if (Objects.equals(action, receiver.action)) {
                    if (Objects.isNull(receivers)) {
                        receivers = new ArrayList<EventObserverRecord>(DEFAULT_SIZE);
                    }
                    receivers.add(receiver);
                }
            }

            if (!Objects.isNull(receivers)) {
                // clear state
                for (int i = 0; i < receivers.size(); i++) {
                    receivers.get(i).broadcasting = false;
                }
                mPendingBroadcasts.add(new EventPosterRecord(action, receivers, data));
            }
        }
        executePendingBroadcasts(mPendingBroadcasts);
    }

    public void postToMain(Object action) {

    }

    public void postToMain(Object action, Object data) {

    }

    private void executePendingBroadcasts(List<EventPosterRecord> pendingBroadcasts) {
        while (true) {
            EventPosterRecord[] brs = null;
            final int N = pendingBroadcasts.size();
            if (N <= 0) {
                return;
            }
            brs = new EventPosterRecord[N];
            for (int i = 0; i < brs.length; i++) {
                EventPosterRecord br = brs[i];
                for (int j = 0; j < br.observers.size(); j++) {
                    EventObserverRecord eventObserverRecord = br.observers.get(j);
                    eventObserverRecord.subscription.invoke(br.data);
                }
            }
        }
    }

}
