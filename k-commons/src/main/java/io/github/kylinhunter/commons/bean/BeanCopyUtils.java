package io.github.kylinhunter.commons.bean;

import org.springframework.beans.BeanUtils;

import io.github.kylinhunter.commons.bean.copy.BeanCopy;
import io.github.kylinhunter.commons.bean.copy.BeanCopyCache;
import io.github.kylinhunter.commons.exception.inner.FormatException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 **/
@Slf4j
public class BeanCopyUtils {

    /**
     * @param source source
     * @param target target
     * @return void
     * @title copyProperties
     * @description
     * @author BiJi'an
     * @date 2021/8/13 8:52 下午
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        try {
            BeanUtils.copyProperties(source, target, ignoreProperties);
            BeanCopy beanCopy = BeanCopyCache.getBeanCopy(source.getClass(), target.getClass());
            if (beanCopy != null) {
                beanCopy.copy(source, target);
            }
        } catch (Exception e) {
            throw new FormatException("copyProperties error", e);
        }
    }

}
