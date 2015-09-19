package androidrubick.xframework.impl.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidrubick.text.Strings;

/**
 * 标识API请求参数中的字段作为是作为Header信息的。
 *
 * <br/>
 *
 * 其中，{@link #name()}指定Header信息的名称，{@link #value()}指定Header信息的值
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/18.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface APIHeader {

    /**
     * 不设置将以字段名作为Header名称
     */
    String name() default Strings.EMPTY;

    /**
     * 设置Header的值
     */
    String value();
}
