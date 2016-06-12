package androidrubick.app.events.internal;

import java.util.List;

/**
 * 中间对象，携带事件及数据。
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/12 0012.
 */
public class EventPosterRecord {
    final Object action;
    final Object data;
    final List<EventObserverRecord> observers;

    EventPosterRecord(Object _action, List<EventObserverRecord> _observers, Object _data) {
        action = _action;
        observers = _observers;
        data = _data;
    }
}