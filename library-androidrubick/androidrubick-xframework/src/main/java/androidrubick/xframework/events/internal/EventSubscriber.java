package androidrubick.xframework.events.internal;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

import androidrubick.utils.Objects;
import androidrubick.utils.Primitives;
import androidrubick.utils.Reflects;

/**
 * 封装反射调用方法
 */
final class EventSubscriber extends WeakReference<Object> {

    private static final ReferenceQueue<Object> sQueue = new ReferenceQueue<Object>();
    private static SubscriptionMonitor sMonitor;

    static void setSubscriptionMonitor(SubscriptionMonitor monitor) {
        sMonitor = monitor;
    }

    /**
     * 检查有无已经回收的对象
     */
    static void checkRelease() {
        if (null == sMonitor) {
            return;
        }
        EventSubscriber eventSubscriber;
        while(null != (eventSubscriber = (EventSubscriber) sQueue.poll())) {
            sMonitor.onInstanceReleased(eventSubscriber);
        }
    }

    private final int hash;
    final Method subscriberMethod;
    final Class<?>[] subscriberMethodParameters;

    EventSubscriber(Object subscriber, Method subscriberMethod) {
        super(subscriber, sQueue);
        this.subscriberMethod = subscriberMethod;
        this.subscriberMethodParameters = subscriberMethod.getParameterTypes();
        this.hash = Objects.hash(subscriber, this.subscriberMethod, this.subscriberMethod);
    }

    /**
     * 调用方法
     *
     * @param data 事件触发时传入的数据，可能为null
     */
    public void invoke(Object data) {
        checkRelease();
        Object subscriber = get();
        if (null == subscriber) {
            return ;
        }
        final Class<?>[] parameterClasses = this.subscriberMethodParameters;
        final Object[] parameterVals = Reflects.genDefaultMethodArgsByParameters(parameterClasses);
        if (!Objects.isNull(data) && !Objects.isEmpty(parameterClasses)) {
            Class<?> clzOfData = data.getClass();
            for (int i = 0; i < parameterClasses.length; i++) {
                Class<?> wappedParameterClz = Primitives.wrap(parameterClasses[i]);
                if (Objects.equals(clzOfData, wappedParameterClz)) {
                    parameterVals[i] = data;
                    break;
                }
            }
        }
        Reflects.invoke(subscriber, subscriberMethod, parameterVals);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof EventSubscriber) {
            EventSubscriber otherEventSubscriber = (EventSubscriber) other;
            return (this == otherEventSubscriber) ||
                    (/*Objects.equals(subscriber, otherSubscription.subscriber)
                        && */Objects.equals(subscriberMethod, otherEventSubscriber.subscriberMethod)
                        && Objects.deepEquals(subscriberMethodParameters, otherEventSubscriber.subscriberMethodParameters));
        } else {
            return false;
        }
    }

}