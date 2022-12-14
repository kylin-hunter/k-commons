package io.github.kylinhunter.commons.exception.info;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @description  ErrInfoAware
*
* @author BiJi'an
*
* @date 2022/11/8
*
**/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ErrInfoAware {
}
