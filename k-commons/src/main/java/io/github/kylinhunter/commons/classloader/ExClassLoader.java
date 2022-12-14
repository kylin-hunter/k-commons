package io.github.kylinhunter.commons.classloader;

import java.net.URL;
import java.net.URLClassLoader;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-06-13 20:10
 **/
@Slf4j
class ExClassLoader extends URLClassLoader {

    private static final ExClassLoader singletion = new ExClassLoader();

    private ExClassLoader() {
        super(new URL[] {}, ExClassLoader.class.getClassLoader());
    }

    public static ExClassLoader getInstance() {
        return singletion;
    }

}
