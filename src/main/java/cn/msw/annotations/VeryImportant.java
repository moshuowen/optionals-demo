package cn.msw.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * @author moshu
 */
@Target({TYPE, METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface VeryImportant {

}
