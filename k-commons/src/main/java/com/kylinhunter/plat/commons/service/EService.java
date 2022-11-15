package com.kylinhunter.plat.commons.service;

/**
 * @author BiJi'an
 * @description a definition for simple service
 * @date 2022/1/1
 **/
public interface EService<T> {
    Class<? extends T> getClazz();
}
