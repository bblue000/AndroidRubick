package androidrubick.xframework.events.internal;

import androidrubick.utils.ToStringHelper;

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
        return new ToStringHelper(getClass().getSimpleName())
                .add("eventSubscriber", eventSubscriber)
                .add("action", action)
                .toString();
    }
}