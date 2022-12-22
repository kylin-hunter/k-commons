package io.github.kylinhunter.commons.yaml;

import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;

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
        try (InputStream inputStream = ResourceHelper.getInputStreamInClassPath(path)) {
            return new Yaml().loadAs(inputStream, clazz);
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

        try {
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
     * @date 2022-11-01 22:01
     */
    public static String dumpAsMap(Object obj) {
        try {
            return new Yaml().dumpAsMap(obj);
        } catch (Exception e) {
            throw new ParamException("dumpAsMap error ", e);
        }

    }
}


