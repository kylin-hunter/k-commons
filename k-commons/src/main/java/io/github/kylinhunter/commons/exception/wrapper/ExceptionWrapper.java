package io.github.kylinhunter.commons.exception.wrapper;

import io.github.kylinhunter.commons.exception.embed.biz.BizException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionWrapper {

  /**
   * @return The wrapped exception type(s) you want throw onward.
   */
  Class<? extends Throwable> value() default BizException.class;

  int code() default 0;

  String msg() default "";

}