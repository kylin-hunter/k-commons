package io.github.kylinhunter.commons.yaml;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.reflections.ReflectionUtils;
import org.yaml.snakeyaml.Yaml;

import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import io.github.kylinhunter.commons.exception.embed.ParamException;
import io.github.kylinhunter.commons.io.ResourceHelper;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-01 22:01
 **/
public class YamlHelper {

    /**
     * @param clazz clazz
     * @param path  path
     * @return T
     * @title loadFromClassPath
     * @description
     * @author BiJi'an
     * @date 2022-11-01 22:01
     */
    public static <T> T loadFromClassPath(Class<T> clazz, String path) {
        return loadFromClassPath(clazz, path, null);
    }

    /**
     * @param clazz clazz
     * @param path  path
     * @return T
     * @title loadFromClassPath
     * @description
     * @author BiJi'an
     * @date 2022-11-01 22:01
     */
    public static <T> T loadFromClassPath(Class<T> clazz, String path, LoadCorrector loadCorrector) {
        try (InputStream inputStream = ResourceHelper.getInputStreamInClassPath(path)) {
            if (inputStream == null) {
                throw new ParamException(" inputStream is null ,invalid path: " + path);
            }
            if (loadCorrector != null) {
                String text = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                return loadFromText(clazz, text, loadCorrector);
            }
            return new Yaml().loadAs(inputStream, clazz);
        } catch (KRuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new ParamException("loadFromClassPath error ,invalid path: " + path, e);
        }
    }

    /**
     * @param clazz clazz
     * @param text  text
     * @return T
     * @title loadFromText
     * @description
     * @author BiJi'an
     * @date 2022-11-01 22:01
     */
    public static <T> T loadFromText(Class<T> clazz, String text) {
        return loadFromText(clazz, text, null);

    }

    /**
     * @param clazz clazz
     * @param text  text
     * @return T
     * @title loadFromText
     * @description
     * @author BiJi'an
     * @date 2022-11-01 22:01
     */
    public static <T> T loadFromText(Class<T> clazz, String text, LoadCorrector loadCorrector) {

        try {
            if (loadCorrector != null) {
                text = loadCorrector.apply(text);
            }
            return new Yaml().loadAs(text, clazz);
        } catch (Exception e) {
            throw new ParamException("loadFromText error ,invalid text: " + text, e);
        }

    }

    /**
     * @param obj obj
     * @return java.lang.String
     * @title dumpAsMap
     * @description
     * @author BiJi'an
     * @date 2023-01-04 10:44
     */
    public static String dumpAsMap(Object obj) {
        return dumpAsMap(obj, null);
    }

    /**
     * @param obj obj
     * @return java.lang.String
     * @title dumpAsMap
     * @description
     * @author BiJi'an
     * @date 2022-11-01 22:01
     */
    @SuppressWarnings("unchecked")
    public static <T> String dumpAsMap(T obj, DumpCorrector<T> dumpCorrector) {
        try {
            if (obj instanceof Cloneable) {
                final Method clone = obj.getClass().getDeclaredMethod("clone");
                obj = (T) ReflectionUtils.invoke(clone, obj);
            }
            obj = dumpCorrector != null ? dumpCorrector.apply(obj) : obj;
            return new Yaml().dumpAsMap(obj);
        } catch (Exception e) {
            throw new ParamException("dumpAsMap error ", e);
        }

    }
}


