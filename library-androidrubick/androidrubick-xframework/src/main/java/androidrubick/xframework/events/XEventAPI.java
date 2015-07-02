package androidrubick.xframework.events;

import androidrubick.xframework.events.internal.XEventAnnotationProcessor;

/**
 * 事件API。
 *
 * <p/>
 *
 * 第一版
 *
 * <p/><p/>
 *
 * · 使用Annotation——{@link androidrubick.xframework.events.annotation.XEvent}标识
 *
 * 方法，当Annotation——{@link androidrubick.xframework.events.annotation.XEvent#value()}指定的
 *
 * 事件（未指定，以方法作为事件标识）触发时，将会调用目标方法。
 *
 * <br/>
 *
 * · 使用<code>post</code>系列方法触发事件。触发事件时，可以携带数据，如果<code>XEvent</code>标识的方法参数中
 *
 * 存在相同数据类型，则将携带的数据赋值给方法参数。
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/14.
 */
public class XEventAPI {

    private XEventAPI() { /* no instance needed */ }

    /**
     * 解析对象所属类方法中的Annotation——{@link androidrubick.xframework.events.annotation.XEvent}
     */
    public static void inject(Object target) {
        XEventAnnotationProcessor.inject(target);
    }

//    /**
//     * 注册事件观察
//     *
//     * @param observer 事件观察者
//     * @param actions 事件标识
//     */
//    public static void register(Object observer, Object...actions) {
//
//    }

//    /**
//     * 注销<code>observer</code>对应的所有事件观察
//     *
//     * @param observer 事件观察者
//     * @param actions 事件观察者
//     */
//    public static void unregister(Object observer, Object...actions) {
//
//    }

    /**
     * 派发事件，未携带任何数据
     * @param action
     */
    public static void post(Object action) {
        post(action, null);
    }

    public static void post(Object action, Object data) {
        XEventAnnotationProcessor.post((String) action, data);
    }

    public static void postToMain(Object action) {
        postToMain(action, null);
    }

    public static void postToMain(Object action, Object data) {
        XEventAnnotationProcessor.postToMain((String) action, data);
    }

}
