package io.github.kylinhunter.commons.json;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import io.github.kylinhunter.commons.date.DatePatterns;
import io.github.kylinhunter.commons.exception.embed.FormatException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-21 19:09
 **/
@Slf4j
public class ObjectMapperProxy {

    public static ObjectMapper defaultObjectMapper;
    public static ObjectMapper snakeObjectMapper;
    public static final byte[] EMPTY_BYTES = new byte[0];

    static {
        defaultObjectMapper = createDefaultObjectMapper();
        snakeObjectMapper = createSnakeObjectMapper();
    }

    /**
     * @return com.fasterxml.jackson.databind.ObjectMapper
     * @title createSnakeObjectMapper
     * @description
     * @author BiJi'an
     * @date 2022-11-21 23:30
     */
    private static ObjectMapper createSnakeObjectMapper() {
        snakeObjectMapper = createDefaultObjectMapper();
        snakeObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return snakeObjectMapper;
    }

    /**
     * @return com.fasterxml.jackson.databind.ObjectMapper
     * @title createObjectMapper
     * @description
     * @author BiJi'an
     * @date 2022-11-21 19:10
     */
    private static ObjectMapper createDefaultObjectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        objectMapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        SimpleDateFormat myDateFormat = new SimpleDateFormat(DatePatterns.DATE_TIME);
        objectMapper.setDateFormat(myDateFormat);
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DatePatterns.DATE_TIME);
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        objectMapper.registerModule(javaTimeModule);

        return objectMapper;
    }

    /**
     * @param jsonOption jsonOption
     * @return com.fasterxml.jackson.databind.ObjectMapper
     * @title getObjectMapper
     * @description
     * @author BiJi'an
     * @date 2022-11-21 20:08
     */
    public static ObjectMapper getObjectMapper(JsonOption jsonOption) {
        if (jsonOption != null && jsonOption.isSnake()) {
            return snakeObjectMapper;
        } else {
            return defaultObjectMapper;
        }
    }

    /**
     * @return com.fasterxml.jackson.databind.ObjectMapper
     * @title getObjectMapper
     * @description
     * @author BiJi'an
     * @date 2022-11-21 20:43
     */
    public static ObjectMapper getDefaultObjectMapper() {
        return defaultObjectMapper;
    }

    /**
     * @param jsonOption jsonOption
     * @param content    content
     * @param javaType   javaType
     * @return T
     * @title readValue
     * @description
     * @author BiJi'an
     * @date 2022-11-21 20:08
     */

    public static <T> T readValue(String content, JavaType javaType, JsonOption jsonOption) {
        try {
            return getObjectMapper(jsonOption).readValue(content, javaType);
        } catch (Exception e) {
            if (jsonOption.isThrowIfFailed()) {
                throw new FormatException("json readValue error", e);
            } else {
                log.error("json readValue error", e);
            }
        }
        return null;
    }

    /**
     * @param jsonOption jsonOption
     * @param content    content
     * @param valueType  valueType
     * @return T
     * @title readValue
     * @description
     * @author BiJi'an
     * @date 2022-11-21 20:08
     */

    public static <T> T readValue(String content, Class<T> valueType, JsonOption jsonOption) {
        try {
            return getObjectMapper(jsonOption).readValue(content, valueType);
        } catch (Exception e) {
            if (jsonOption.isThrowIfFailed()) {
                throw new FormatException("json readValue error", e);
            } else {
                log.error("json readValue error", e);
            }
        }
        return null;
    }

    /**
     * @param content    content
     * @param valueType  valueType
     * @param jsonOption jsonOption
     * @return T
     * @title readValue
     * @description
     * @author BiJi'an
     * @date 2022-11-21 20:14
     */

    public static <T> T readValue(byte[] content, Class<T> valueType, JsonOption jsonOption) {
        try {
            return getObjectMapper(jsonOption).readValue(content, valueType);
        } catch (Exception e) {
            if (jsonOption.isThrowIfFailed()) {
                throw new FormatException("json readValue error", e);
            } else {
                log.error("json readValue error", e);
            }
        }
        return null;
    }

    /**
     * @param content    content
     * @param jsonOption jsonOption
     * @return java.lang.String
     * @title writeValueAsString
     * @description
     * @author BiJi'an
     * @date 2022-11-21 20:14
     */
    public static String writeValueAsString(Object content, JsonOption jsonOption) {
        try {
            return getObjectMapper(jsonOption).writeValueAsString(content);
        } catch (Exception e) {
            if (jsonOption.isThrowIfFailed()) {
                throw new FormatException("json readValue error", e);
            } else {
                log.error("json readValue error", e);
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * @param content    content
     * @param jsonOption jsonOption
     * @return byte[]
     * @title writeValueAsBytes
     * @description
     * @author BiJi'an
     * @date 2022-11-21 20:14
     */
    public static byte[] writeValueAsBytes(Object content, JsonOption jsonOption) {
        try {
            return getObjectMapper(jsonOption).writeValueAsBytes(content);
        } catch (Exception e) {
            if (jsonOption.isThrowIfFailed()) {
                throw new FormatException("json readValue error", e);
            } else {
                log.error("json readValue error", e);
            }
        }
        return EMPTY_BYTES;
    }
}
