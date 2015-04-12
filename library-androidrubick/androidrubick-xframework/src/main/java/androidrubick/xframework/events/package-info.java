/**
 * 该包定义事件相关的顶层接口，抽象出android应用程序开发中的事件处理。
 *
 * <p/>
 *
 * 该工具中，使用者可能会感觉没有严格封装成<code>Event</code>-<code>EventObserver</code>的典型观察者模式，
 * 而仅仅是指定<code>Event</code>，发送（广播）<code>Event</code>。
 * <p/>
 * 它的目的是简化开发，<code>Event</code>-<code>EventObserver</code>的逻辑由工具内部封装。
 *
 * <p/><p/>
 *
 * v1.0版本：
 * <ul>
 *     <li>事件标识为String类型（类似于{@link android.content.BroadcastReceiver}）</li>
 * </ul>
 *
 * <p/><p/>
 *
 * xframework的宗旨是：简化开发
 */
package androidrubick.xframework.events;