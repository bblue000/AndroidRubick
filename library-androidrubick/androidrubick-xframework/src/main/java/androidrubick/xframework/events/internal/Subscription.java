package androidrubick.xframework.events.internal;

import java.lang.reflect.Method;

import androidrubick.utils.Objects;
import androidrubick.utils.Primitives;
import androidrubick.utils.Reflects;

/**
 * 封装反射调用方法
 */
final class Subscription {
    final Object subscriber;
    final Method subscriberMethod;
    final Class<?>[] subscriberMethodParameters;

    Subscription(Object subscriber, Method subscriberMethod) {
        this.subscriber = subscriber;
        this.subscriberMethod = subscriberMethod;
        this.subscriberMethodParameters = subscriberMethod.getParameterTypes();
    }

    /**
     * 调用方法
     *
     * @param data 事件触发时传入的数据，可能为null
     */
    public void invoke(Object data) {
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
        if (other instanceof Subscription) {
            Subscription otherSubscription = (Subscription) other;
            return (this == otherSubscription) ||
                    (Objects.equals(subscriber, otherSubscription.subscriber)
                        && Objects.equals(subscriberMethod, otherSubscription.subscriberMethod));
        } else {
            return false;
        }
    }

}