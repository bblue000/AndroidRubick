package androidrubick.xframework.impl.api.param;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidrubick.text.Strings;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/18.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface XAPIHeader {

    /**
     * 不设置将以字段名作为Header名称
     */
    String name() default Strings.EMPTY;

    /**
     * 设置Header的值
     */
    String value();
}
