package org.ixming.androidrubick.db4android.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识表对应的类需要解析父类中的字段。
 * 
 * @author Yin Yong
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.TYPE })
public @interface SuperClassIncluded {

}
