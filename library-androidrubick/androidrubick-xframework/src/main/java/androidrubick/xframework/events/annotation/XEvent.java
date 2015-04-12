package androidrubick.xframework.events.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 事件注解
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/11 0011.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface XEvent {

    /**
     * 事件的唯一标识名称（可能有多个）
     */
    String[] value();

//    /**
//     * {@link XEvent}注解之所以不设计为仅仅在方法上使用，是因为有一些情况下，
//     * 当前类的某些方法不能通过父类覆写，而事件触发时恰好需要调用这些方法，可以通过该方式指定
//     *
//     * <p/>
//     * 注意点：
//     * <ul>
//     * 	<li>当本Annotation使用在类上时才有效；
//     * 	<li>如果设值，只识别第一项作为本事件的处理方法。
//     * </ul>
//     */
//    XEventMethod[] method() default { };
}
