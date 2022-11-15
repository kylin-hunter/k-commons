package com.kylinhunter.plat.commons.service;

import com.kylinhunter.plat.commons.exception.inner.InitException;
import com.kylinhunter.plat.commons.util.ReflectionUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022/01/01
 **/
@Slf4j
public class DefaultEService<T extends Enum<T>, R> {
    private final Object[] services;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public DefaultEService() {
        Class<T> clazz = ReflectionUtil.getSuperClassGenericType(this.getClass(), 0);

        Enum<T>[] enumConstants = clazz.getEnumConstants();
        if (enumConstants == null || enumConstants.length <= 0) {
            throw new InitException("no enum found");
        }
        services = new Object[enumConstants.length];
        for (Enum<T> e : enumConstants) {
            if (e instanceof EService) {
                this.register(e, ((EService) e).getClazz());
            } else {
                throw new InitException("init service error:" + e);
            }

        }

    }

    /**
     * @param e e
     * @return R
     * @title get
     * @description
     * @author BiJi'an
     * @date 2022-01-01 23:55
     */
    @SuppressWarnings({"unchecked"})
    public R get(Enum<T> e) {

        Object service = services[e.ordinal()];
        if (service != null) {
            return (R) service;
        } else {
            throw new InitException(" invalid enumService:" + e.getClass().getName());
        }

    }

    /**
     * @param e     e
     * @param clazz clazz
     * @title setService
     * @description
     * @author BiJi'an
     * @date 2022-01-01 23:49
     */
    protected void register(Enum<T> e, Class<R> clazz) {

        try {
            if (clazz == null) {
                throw new InitException("clazz can't be null");
            }
            R service = clazz.newInstance();
            services[e.ordinal()] = service;
        } catch (Exception ex) {
            throw new InitException("init SimpleService error", ex);
        }
    }

}
