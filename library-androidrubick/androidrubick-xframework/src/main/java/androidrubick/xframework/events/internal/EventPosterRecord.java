package androidrubick.xframework.events.internal;

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

    EventPosterRecord(Object _action, List<EventObserverRecord> _receivers, Object _data) {
        action = _action;
        observers = _receivers;
        data = _data;
    }
}