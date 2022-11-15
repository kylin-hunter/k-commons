package com.kylinhunter.plat.commons.classloader;

import java.net.URL;
import java.net.URLClassLoader;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-06-13 20:10
 **/
@Slf4j
class KPlatClassLoader extends URLClassLoader {

    KPlatClassLoader() {
        super(new URL[] {}, KPlatClassLoader.class.getClassLoader());
    }

}
