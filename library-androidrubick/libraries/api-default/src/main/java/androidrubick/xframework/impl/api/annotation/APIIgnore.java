package androidrubick.xframework.impl.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识API请求参数对象中的字段跟API请求无关，忽略处理
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/19 0019.
 *
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface APIIgnore {
}
