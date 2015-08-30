package androidrubick.xbase.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注相应的元素只是用于测试
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/18.
 *
 * @since 1.0
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD,
        ElementType.LOCAL_VARIABLE, ElementType.METHOD,
        ElementType.PACKAGE, ElementType.PARAMETER, ElementType.TYPE})
public @interface ForTest {
}
