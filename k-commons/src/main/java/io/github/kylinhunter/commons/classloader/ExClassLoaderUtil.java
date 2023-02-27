package io.github.kylinhunter.commons.classloader;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

import io.github.kylinhunter.commons.exception.embed.GeneralException;
import io.github.kylinhunter.commons.exception.embed.SystemException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-06-13 20:10
 **/
@Slf4j
public class ExClassLoaderUtil {

    /**
     * @param path path
     * @return void
     * @title addClassPath
     * @description
     * @author BiJi'an
     * @date 2022-11-19 02:10
     */
    public static void addClassPath(Path path) {

        try {

            ExClassLoader exClassLoader = ExClassLoader.getInstance();
            Method add = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            add.setAccessible(true);
            add.invoke(exClassLoader, path.toUri().toURL());
            ClassLoader parentClassLoader = exClassLoader.getParent();
            if (parentClassLoader instanceof URLClassLoader) {
                URLClassLoader classLoader = (URLClassLoader) parentClassLoader;
                add.invoke(classLoader, path.toUri().toURL());
            }

            log.info("add addClassPath ==>" + path);
        } catch (Exception e) {
            throw new SystemException("addClassPath error", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> loadClass(String clazz) {
        try {
            return (Class<T>) ExClassLoader.getInstance().loadClass(clazz);
        } catch (Exception e) {
            throw new GeneralException("loadClass error", e);
        }
    }

}
