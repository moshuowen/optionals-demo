package cn.msw.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author moshu
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.FIELD})
public @interface ImportantString {
}
