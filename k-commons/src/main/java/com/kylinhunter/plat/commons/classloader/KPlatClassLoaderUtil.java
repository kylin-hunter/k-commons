package com.kylinhunter.plat.commons.classloader;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

import com.kylinhunter.plat.commons.exception.inner.GeneralException;
import com.kylinhunter.plat.commons.exception.inner.SystemException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-06-13 20:10
 **/
@Slf4j
public class KPlatClassLoaderUtil {
    private static KPlatClassLoader kPlatClassLoader = new KPlatClassLoader();

    public static void addClassPath(Path path, boolean parent) {

        try {
            Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] {URL.class});
            add.setAccessible(true);
            add.invoke(kPlatClassLoader, path.toUri().toURL());

            if (parent && kPlatClassLoader.getParent() != null) {
                ClassLoader parentClassLoader = kPlatClassLoader.getParent();
                if (parentClassLoader instanceof URLClassLoader) {
                    URLClassLoader classLoader = (URLClassLoader) parentClassLoader;
                    add.invoke(classLoader, path.toUri().toURL());
                }
            }

            log.info("add addClassPath ==>" + path);
        } catch (Exception e) {
            throw new SystemException("addClassPath error", e);
        }
    }

    public static <T> Class<T> loadClass(String clazz) {
        try {
            return (Class<T>) kPlatClassLoader.loadClass(clazz);
        } catch (Exception e) {
            throw new GeneralException("loadClass error", e);
        }
    }

}
