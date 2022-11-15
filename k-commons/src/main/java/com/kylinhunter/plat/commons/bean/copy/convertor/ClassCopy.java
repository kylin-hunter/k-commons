package com.kylinhunter.plat.commons.bean.copy.convertor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ClassCopy {
    Class<? extends ClassCopyConvertor<?, ?>> value();
    Class<?>[] targets();
}
