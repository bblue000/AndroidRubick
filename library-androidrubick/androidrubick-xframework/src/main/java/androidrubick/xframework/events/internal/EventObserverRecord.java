package androidrubick.xframework.events.internal;

/**
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/12 0012.
 */
/*package*/ class EventObserverRecord {

    final Object action;
    final EventSubscriber eventSubscriber;
    // temp state
    boolean broadcasting;

    public EventObserverRecord(Object _action, EventSubscriber _eventSubscriber) {
        super();
        action = _action;
        eventSubscriber = _eventSubscriber;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        builder.append("EventObserver{");
        builder.append(eventSubscriber);
        builder.append(" action=");
        builder.append(action);
        builder.append("}");
        return builder.toString();
    }
}