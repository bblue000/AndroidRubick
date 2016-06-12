package androidrubick.app.events;

/**
 *
 * 事件API的接口
 *
 * @param <EventAction> 事件标识
 * @param <EventData> 事件数据
 * @param <EventObserver> 事件观察者类型
 */
public interface IEventAPI<EventAction, EventData, EventObserver> {

    /**
     * 注册事件观察
     *
     * @param observer 事件观察者
     * @param actions 事件标识
     */
	public void register(EventObserver observer, EventAction... actions);

	/**
	 * 注销<code>observer</code>对应的所有事件观察
	 * 
	 * @param observer 事件观察者
	 * @param actions 事件观察者
	 */
	public void unregister(EventObserver observer, EventAction... actions);

    /**
     * 发送事件（在当前线程中处理）
     *
     * @param event 事件标志
     */
    public void post(EventAction event);

    /**
     * 发送事件（在当前线程中处理）
     *
     * @param event 事件标志
     */
	public void post(EventAction event, EventData data);

    /**
     * 发送事件（在主线程中处理）
     *
     * @param event 事件标志
     */
    public void postToMain(EventAction event);

    /**
     * 发送事件（在主线程中处理）
     *
     * @param event 事件标志
     * @param data 事件携带的数据，数据将根据回调方法
     */
	public void postToMain(EventAction event, EventData data);

}
