package androidrubick.xframework.events.internal;

/**
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/12 0012.
 */
/*package*/ class EventObserverRecord {

    final Object action;
    final Subscription subscription;
    // temp state
    boolean broadcasting;

    public EventObserverRecord(Object _action, Subscription _subscription) {
        super();
        action = _action;
        subscription = _subscription;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        builder.append("EventObserver{");
        builder.append(subscription);
        builder.append(" action=");
        builder.append(action);
        builder.append("}");
        return builder.toString();
    }
}