package androidrubick.xframework.events;

/**
 *
 * 事件API的接口
 *
 * @param <EventData> 事件数据类型
 * @param <EventObserver> 事件观察者类型
 */
public interface IEventAPI<EventData, EventObserver> {

    /**
     * 注册事件观察
     *
     * @param event 事件标识
     * @param observer 事件观察者
     */
	public void register(String event, EventObserver observer);
	
	/**
	 * 注销<code>observer</code>对应的所有事件观察
	 * 
	 * @param observer 事件观察者
	 */
	public void unregister(EventObserver observer);
	
	public void post(String event, EventData data);
	
	public void postToMain(String event, EventData data);


    public EventData createEventData();

}
