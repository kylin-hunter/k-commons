package io.github.kylinhunter.commons.json;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JavaType;

import io.github.kylinhunter.commons.exception.inner.FormatException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description json工具
 * @date 2022/01/01
 **/
@Slf4j
public class JsonUtils {


    /**
     * @param content    content
     * @param valueType  valueType
     * @param jsonOption jsonOption
     * @return T
     * @title readToObject
     * @description
     * @author BiJi'an
     * @date 2022-11-21 19:02
     */
    public static <T> T readToObject(String content, Class<T> valueType, JsonOption jsonOption) {
        return ObjectMapperProxy.readValue(content, valueType, jsonOption);
    }

    /**
     * @param content content
     * @param type    type
     * @return T
     * @title toObject
     * @description
     * @author BiJi'an
     * @date 2022-11-21 16:30
     */

    public static <T> T readToObject(String content, Class<T> type) {
        return readToObject(content, type, JsonOptions.DEFAULT);
    }

    /**
     * @param data       data
     * @param type       userType
     * @param jsonOption jsonOption
     * @return T
     * @title toObject
     * @description
     * @author BiJi'an
     * @date 2022-05-20 00:37
     */
    public static <T> T readToObject(byte[] data, Class<T> type, JsonOption jsonOption) {

        return ObjectMapperProxy.readValue(data, type, jsonOption);

    }

    /**
     * @param data data
     * @param type type
     * @return T  t
     * @title toObject
     * @description
     * @author BiJi'an
     * @date 2022-11-21 16:26
     */
    public static <T> T readToObject(byte[] data, Class<T> type) {
        return readToObject(data, type, JsonOptions.DEFAULT);
    }

    /**
     * @param value      value
     * @param jsonOption jsonOption
     * @return java.lang.String
     * @title toString
     * @description
     * @author BiJi'an
     * @date 2022-11-21 16:32
     */
    public static String writeToString(Object value, JsonOption jsonOption) {
        return ObjectMapperProxy.writeValueAsString(value, jsonOption);
    }

    /**
     * @param value value
     * @return java.lang.String
     * @title writeToString
     * @description
     * @author BiJi'an
     * @date 2022-11-21 19:18
     */

    public static String writeToString(Object value) {
        return writeToString(value, JsonOptions.FAIL_NO_SNAKE);
    }

    /**
     * @param source source
     * @return byte[]
     * @title toBytes
     * @description
     * @author BiJi'an
     * @date 2022/01/01 4:29 下午
     */
    public static byte[] wirteToBytes(Object source) {
        return wirteToBytes(source, JsonOptions.DEFAULT);
    }

    /**
     * @param source     source
     * @param jsonOption jsonOption
     * @return byte[]
     * @title 对象转字节
     * @description
     * @author BiJi'an
     * @date 2022/01/01 4:29 下午
     */
    public static byte[] wirteToBytes(Object source, JsonOption jsonOption) {
        return ObjectMapperProxy.writeValueAsBytes(source, jsonOption);
    }

    /**
     * @param content    content
     * @param jsonOption jsonOption
     * @return java.util.Map
     * @title toMap
     * @description
     * @author BiJi'an
     * @date 2022/01/01 4:30 下午
     */
    public static Map<String, Object> readToMap(String content, JsonOption jsonOption) {
        return ObjectMapperProxy.readValue(content, Map.class, jsonOption);
    }

    /**
     * @param content content
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @title toMap
     * @description
     * @author BiJi'an
     * @date 2022-11-21 19:19
     */
    public static Map<String, Object> readToMap(String content) {
        return readToMap(content, JsonOptions.DEFAULT);
    }

    /**
     * @param content    content
     * @param valueType  valueType
     * @param jsonOption jsonOption
     * @return T
     * @title readValue
     * @description
     * @author BiJi'an
     * @date 2022-11-21 20:15
     */
    public static <T> T readValue(String content, JavaType valueType, JsonOption jsonOption) {

        return ObjectMapperProxy.readValue(content, valueType, jsonOption);

    }

    /**
     * @param content   content
     * @param valueType valueType
     * @return T
     * @title readValue
     * @description
     * @author BiJi'an
     * @date 2022-11-21 20:44
     */
    public static <T> T readValue(String content, JavaType valueType) {
        return readValue(content, valueType, JsonOptions.DEFAULT);

    }

    /**
     * @param content
     * @param clazz
     * @param jsonOption
     * @return java.util.List<T>
     * @throws
     * @title toList
     * @description
     * @author BiJi'an
     * @date 2022-11-21 20:45
     */

    public static <T> List<T> toList(String content, Class<T> clazz, JsonOption jsonOption) {
        try {
            JavaType javaType =
                    ObjectMapperProxy.getObjectMapper().getTypeFactory().constructCollectionType(List.class, clazz);
            return ObjectMapperProxy.readValue(content, javaType, jsonOption);
        } catch (Exception e) {
            log.error("json readValue error", e);
            if (jsonOption.isThrowIfFailed()) {
                throw new FormatException("json readValue error", e);
            }
        }
        return null;
    }

    /**
     * @param parametrized     parametrized
     * @param parameterClasses parameterClasses
     * @return com.fasterxml.jackson.databind.JavaType
     * @title constructParametricType
     * @description
     * @author BiJi'an
     * @date 2022-11-21 16:44
     */

    public static JavaType constructCollectionType(Class<?> parametrized, Class<?>... parameterClasses) {
        return ObjectMapperProxy.getObjectMapper(null).getTypeFactory()
                .constructParametricType(parametrized, parameterClasses);
    }

}
