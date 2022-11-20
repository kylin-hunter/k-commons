package io.github.kylinhunter.commons.bean.copy.convertor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ClassCopy {
    /**
     * @return java.lang.Class<? extends io.github.kylinhunter.commons.bean.copy.convertor.ClassConvertor < ?, ?>>
     * @title value
     * @description
     * @author BiJi'an
     * @date 2022-11-19 01:04
     */
    Class<? extends ClassConvertor<?, ?>> value();

    /**
     * @return java.lang.Class<?>[]
     * @title targets
     * @description
     * @author BiJi'an
     * @date 2022-11-19 01:04
     */
    Class<?>[] targets();
}
