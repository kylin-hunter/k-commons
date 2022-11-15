package com.kylinhunter.plat.commons.service;

import java.util.Map;

import com.google.common.collect.Maps;
import com.kylinhunter.plat.commons.exception.inner.InitException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022/01/01
 **/
@Slf4j
public class EServices {
    private static final Map<Enum<?>, Object> services = Maps.newHashMap();

    /**
     * @param e e
     * @return R
     * @title get
     * @description
     * @author BiJi'an
     * @date 2022-01-01 23:55
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>, R> R get(Enum<T> e) {

        Object service = services.get(e);
        if (service != null) {
            return (R) service;
        } else {
            if (e instanceof EService) {
                synchronized(EService.class) {
                    service = services.get(e);
                    if (service != null) {
                        return (R) service;
                    } else {
                        Class<R> clazz = (Class<R>) ((EService<?>) e).getClazz();
                        return register(e, clazz);

                    }
                }
            } else {
                throw new InitException(" invalid enumService:" + e.getClass().getName());
            }
        }

    }

    /**
     * @param e     e
     * @param clazz clazz
     * @return T
     * @title setService
     * @description
     * @author BiJi'an
     * @date 2022-01-01 23:49
     */
    public static <T extends Enum<T>, R> R register(Enum<T> e, Class<R> clazz) {

        try {
            if (clazz == null) {
                throw new InitException("clazz can't be null");
            }
            R service = clazz.newInstance();
            services.put(e, service);
            return service;
        } catch (Exception ex) {
            throw new InitException("init SimpleService error", ex);
        }
    }

}
