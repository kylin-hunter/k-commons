package com.kylinhunter.plat.commons.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.kylinhunter.plat.commons.exception.inner.FormatException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description json工具
 * @date 2022/01/01
 **/
@Slf4j
public class JsonUtils {

    public static ObjectMapper objectMapperCommon;
    public static ObjectMapper objectMapperSnake;
    public static JavaType listMapDataType;
    public static final byte[] EMPTY_BYTES = new byte[0];

    static {
        objectMapperCommon = createObjectMapper();
        listMapDataType = objectMapperCommon.getTypeFactory().constructParametricType(ArrayList.class, Map.class);
        objectMapperSnake = createObjectMapper();
        objectMapperSnake.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

    }

    public static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        //        objectMapperCommon.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //        objectMapperCommon.enable(SerializationFeature.INDENT_OUTPUT);

        // 允许json中包含非引号控制字符
        objectMapper.configure(
                JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(),
                true
        );
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 设置JSON时间格式
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(myDateFormat);
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        String defaultDateTimeFormat = "yyyy-MM-dd HH:mm:ss";

        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(defaultDateTimeFormat)));
        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(defaultDateTimeFormat)));

        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

    public static JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
        return objectMapperCommon.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }

    public static <T> T toObject(byte[] data, Class<T> type) {
        return toObject(data, type, true);
    }

    public static <T> T toObject(byte[] data, Class<T> type, boolean throwIfFailed) {
        return toObject(data, type, false, throwIfFailed);
    }

    /**
     * @param data          data
     * @param type          userType
     * @param throwIfFailed throwIfFailed
     * @return T
     * @title toObject
     * @description
     * @author BiJi'an
     * @date 2022-05-20 00:37
     */
    public static <T> T toObject(byte[] data, Class<T> type, boolean snake, boolean throwIfFailed) {
        try {
            if (data == null || data.length == 0) {
                throw new FormatException("body is null or empty ");
            }
            if (snake) {
                return objectMapperSnake.readValue(data, type);
            } else {
                return objectMapperCommon.readValue(data, type);

            }
        } catch (Exception e) {
            if (throwIfFailed) {
                throw new FormatException("json toObject error", e);
            } else {
                log.error("json toObject error", e);
            }
        }
        return null;
    }

    public static <T> T toObject(String content, Class<T> type) {
        return toObject(content, type, true);
    }

    /**
     * @param content       content
     * @param valueType     valueType
     * @param throwIfFailed throwIfFailed
     * @return T
     * @title toObject
     * @description
     * @author BiJi'an
     * @date 2022-05-20 00:39
     */
    public static <T> T toObject(String content, Class<T> valueType, boolean throwIfFailed) {

        return toObject(content, valueType, false, throwIfFailed);
    }

    public static <T> T toObject(String content, Class<T> valueType, boolean snake, boolean throwIfFailed) {

        try {
            if (snake) {
                return objectMapperSnake.readValue(content, valueType);
            } else {
                return objectMapperCommon.readValue(content, valueType);
            }
        } catch (Exception e) {
            if (throwIfFailed) {
                throw new FormatException("json toObject error", e);
            } else {
                log.error("json toObject error", e);
            }
        }
        return null;
    }

    /**
     * @param source source
     * @return byte[]
     * @title 对象转字节
     * @description
     * @author BiJi'an
     * @date 2022/01/01 4:29 下午
     */
    public static byte[] toBytes(Object source) {

        try {
            return objectMapperCommon.writeValueAsBytes(source);
        } catch (Exception e) {
            log.error("json toBytes error", e);
            throw new FormatException("json toBytes error", e);

        }
    }

    /**
     * @param source        source
     * @param throwIfFailed throwIfFailed
     * @return byte[]
     * @title 对象转字节
     * @description
     * @author BiJi'an
     * @date 2022/01/01 4:29 下午
     */
    public static byte[] toBytes(Object source, boolean throwIfFailed) {

        try {
            return objectMapperCommon.writeValueAsBytes(source);
        } catch (Exception e) {
            if (throwIfFailed) {
                throw new FormatException("json toObject error", e);
            } else {
                log.error("json toBytes error", e);
            }
        }
        return EMPTY_BYTES;
    }

    public static String toString(Object value) {
        return toString(value, false, true);
    }

    /**
     * @param value         value
     * @param throwIfFailed throwIfFailed
     * @return java.lang.String
     * @title 对象转字符串
     * @description
     * @author BiJi'an
     * @date 2022/01/01 4:29 下午
     */

    public static String toString(Object value, boolean throwIfFailed) {
        return toString(value, false, throwIfFailed);
    }

    public static String toString(Object value, boolean snake, boolean throwIfFailed) {
        try {
            if (snake) {
                return objectMapperSnake.writeValueAsString(value);
            } else {
                return objectMapperCommon.writeValueAsString(value);
            }

        } catch (Exception e) {
            if (throwIfFailed) {
                throw new FormatException("json toString error", e);
            } else {
                log.error("json toString error", e);
            }
        }
        return StringUtils.EMPTY;
    }

    public static Map<String, Object> toMap(String content) {
        return toMap(content, true);
    }

    /**
     * @param content       content
     * @param throwIfFailed throwIfFailed
     * @return java.util.Map
     * @title json字符串转Map
     * @description
     * @author BiJi'an
     * @date 2022/01/01 4:30 下午
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String content, boolean throwIfFailed) {

        try {
            return objectMapperCommon.readValue(content, Map.class);
        } catch (Exception e) {
            if (throwIfFailed) {
                throw new FormatException("json toMap error", e);
            } else {
                log.error("json toString error", e);
            }
        }
        return null;
    }

    public static <T> T readValue(String content, JavaType valueType, boolean throwIfFailed) {

        try {
            return objectMapperCommon.readValue(content, valueType);
        } catch (JsonProcessingException e) {
            if (throwIfFailed) {
                throw new FormatException("json readValue error", e);
            } else {
                log.error("json readValue error", e);
            }
        }
        return null;

    }

//    @SuppressWarnings("unused")
//    public static <T> List<T> toList(String content, Class<T> clazz, boolean throwIfFailed) {
//        try {
//            return objectMapperCommon.readValue(content, new TypeReference<List<T>>() {
//            });
//        } catch (JsonProcessingException e) {
//            if (throwIfFailed) {
//                throw new FormatException("json readValue error", e);
//            } else {
//                log.error("json readValue error", e);
//            }
//        }
//        return null;
//    }

    public static <T> List<T> toList(String content, Class<T> clazz, boolean throwIfFailed) {
        try {
            JavaType javaType = objectMapperCommon.getTypeFactory().constructCollectionType(List.class, clazz);
            return objectMapperCommon.readValue(content, javaType);
        } catch (Exception e) {
            if (throwIfFailed) {
                throw new FormatException("json readValue error", e);
            } else {
                log.error("json readValue error", e);
            }
        }
        return null;
    }


}
