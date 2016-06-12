package androidrubick.app.events.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @hide
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD })
public @interface XEventMethod {
	
	/**
	 * 方法所在的类。
	 * 
	 * <p/>
	 * 
	 * 指定了方法所在的类，将会提高一定的效率；毕竟遍历所有的类获取方法较为耗时
	 * 
	 * <p/>
	 * 注意点：
	 * <ul>
	 * 	<li>如果设值，只识别第一项作为本方法所在的类。
	 * </ul>
	 */
	Class<?>[] ownerClass() default {};
	
	/**
	 * method name
	 */
	String value() default "";

	/**
	 * List of method parameters. If the type is not a primitive it must be
	 * fully-qualified.
	 */
	Class<?>[] parameters() default {};
	
//	/**
//	 * Primative or fully-qualified return type of the listener method. May also
//	 * be {@code void}.
//	 */
//	Class<?> returnType() default void.class;
//
//	/**
//	 * If {@link #returnType()} is not {@code void} this value is returned when
//	 * no binding exists.
//	 */
//	String defaultReturn() default "null";
}
