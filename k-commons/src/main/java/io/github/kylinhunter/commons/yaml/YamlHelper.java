package io.github.kylinhunter.commons.yaml;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
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

    private static final LoadCorrector DEFAULT_LOAD_CORRECTOR = new DefaultLoadCorrector();
    private static final DumpCorrector DEFAULT_DUMP_CORRECTOR = new DefaultDumpCorrector();

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
        return loadFromClassPath(clazz, path, true);
    }

    /**
     * @param clazz     clazz
     * @param path      path
     * @param autoCamel autoCamel
     * @return T
     * @title loadFromClassPath
     * @description
     * @author BiJi'an
     * @date 2023-02-04 16:52
     */
    public static <T> T loadFromClassPath(Class<T> clazz, String path, boolean autoCamel) {
        if (autoCamel) {
            return loadFromClassPath(clazz, path, DEFAULT_LOAD_CORRECTOR);
        } else {
            return loadFromClassPath(clazz, path, null);
        }
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

            String text = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

            return loadFromText(clazz, text, loadCorrector);

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
        return loadFromText(clazz, text, true);

    }

    /**
     * @param clazz     clazz
     * @param text      text
     * @param autoCamel autoCamel
     * @return T
     * @title loadFromText
     * @description
     * @author BiJi'an
     * @date 2023-02-04 16:50
     */
    public static <T> T loadFromText(Class<T> clazz, String text, boolean autoCamel) {
        if (autoCamel) {
            return loadFromText(clazz, text, DEFAULT_LOAD_CORRECTOR);
        } else {
            return loadFromText(clazz, text, null);
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
    public static <T> T loadFromText(Class<T> clazz, String text, LoadCorrector loadCorrector) {

        try {

            if (loadCorrector != null) {
                text = loadCorrector.correct(text, YamlType.CAMLE);
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
     * @date 2023-02-04 00:09
     */
    public static String dumpAsMap(Object obj) {
        return dumpAsMap(obj, null);

    }

    /**
     * @param obj      obj
     * @param yamlType yamlType
     * @return java.lang.String
     * @title dumpAsMap
     * @description
     * @author BiJi'an
     * @date 2023-02-04 00:20
     */

    public static String dumpAsMap(Object obj, YamlType yamlType) {
        if (yamlType != null) {
            return dumpAsMap(obj, yamlType, DEFAULT_DUMP_CORRECTOR);
        } else {
            return dumpAsMap(obj, null, null);

        }

    }

    /**
     * @param obj           obj
     * @param dumpCorrector dumpCorrector
     * @return java.lang.String
     * @title dumpAsMap
     * @description
     * @author BiJi'an
     * @date 2023-02-04 00:09
     */
    public static String dumpAsMap(Object obj, YamlType yamlType, DumpCorrector dumpCorrector) {
        try {
            String text = new Yaml().dumpAsMap(obj);
            if (dumpCorrector != null) {
                return dumpCorrector.correct(text, yamlType);
            } else {
                return text;
            }
        } catch (Exception e) {
            throw new ParamException("dumpAsMap error ", e);
        }

    }
}


