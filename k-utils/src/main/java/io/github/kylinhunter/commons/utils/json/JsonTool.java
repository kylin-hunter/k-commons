package io.github.kylinhunter.commons.utils.json;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kylinhunter.commons.exception.embed.FormatException;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 23:55
 */
public class JsonTool {

  private final JsonOption jsonOption;
  private final ObjectMapper objectMapper;

  private final byte[] EMPTY_BYTES = new byte[0];

  private static final JsonTool jsonTool = new JsonTool(new JsonOption());

  public JsonTool(JsonOption jsonOption) {
    this.jsonOption = jsonOption;
    this.objectMapper = ObjectMapperCreator.create(jsonOption);
  }

  public static JsonTool get() {
    return jsonTool;
  }


  public static JsonToolBuilder builder() {
    return new JsonToolBuilder();
  }

  /**
   * @param content   content
   * @param valueType valueType
   * @return T
   * @title readToObject
   * @description
   * @author BiJi'an
   * @date 2022-11-21 19:02
   */
  public <T> T readToObject(String content, Class<T> valueType) {
    try {
      return objectMapper.readValue(content, valueType);
    } catch (Exception e) {
      if (jsonOption.isThrowIfFailed()) {
        throw new FormatException("text readValue error", e);
      }
    }
    return null;
  }


  /**
   * @param data data
   * @param type userType
   * @return T
   * @title toObject
   * @description
   * @author BiJi'an
   * @date 2022-05-20 00:37
   */
  public <T> T readToObject(byte[] data, Class<T> type) {

    try {
      return objectMapper.readValue(data, type);
    } catch (Exception e) {
      if (jsonOption.isThrowIfFailed()) {
        throw new FormatException("text readValue error", e);
      }
    }
    return null;
  }


  /**
   * @param value value
   * @return java.lang.String
   * @title toString
   * @description
   * @author BiJi'an
   * @date 2022-11-21 16:32
   */
  public String writeToString(Object value) {
    try {
      return objectMapper.writeValueAsString(value);
    } catch (Exception e) {
      if (jsonOption.isThrowIfFailed()) {
        throw new FormatException("text readValue error", e);
      }
    }
    return StringUtil.EMPTY;
  }


  /**
   * @param source source
   * @return byte[]
   * @title writetoBytes
   * @description
   * @author BiJi'an
   * @date 2022/01/01 4:29 下午
   */
  public byte[] writetoBytes(Object source) {
    try {
      return objectMapper.writeValueAsBytes(source);
    } catch (Exception e) {
      if (jsonOption.isThrowIfFailed()) {
        throw new FormatException("text readValue error", e);
      }
    }
    return EMPTY_BYTES;
  }

  /**
   * @param content content
   * @return java.util.Map
   * @title toMap
   * @description
   * @author BiJi'an
   * @date 2022/01/01 4:30 下午
   */
  @SuppressWarnings("unchecked")
  public Map<String, Object> readToMap(String content) {
    try {
      return objectMapper.readValue(content, Map.class);
    } catch (Exception e) {
      if (jsonOption.isThrowIfFailed()) {
        throw new FormatException("text readValue error", e);
      }
    }
    return null;
  }


  /**
   * @param content content
   * @param clazz   clazz
   * @return java.util.List<T>
   * @title toList
   * @description
   * @author BiJi'an
   * @date 2022-11-21 20:45
   */
  public <T> List<T> readToListObject(String content, Class<T> clazz) {
    JavaType javaType = constructCollectionType(List.class, clazz);
    try {
      return objectMapper.readValue(content, javaType);
    } catch (Exception e) {
      if (jsonOption.isThrowIfFailed()) {
        throw new FormatException("text readValue error", e);
      }
    }
    return null;
  }


  /**
   * @param content   content
   * @param valueType valueType
   * @return T
   * @title readValue
   * @description
   * @author BiJi'an
   * @date 2022-11-21 20:15
   */
  public <T> T readValue(String content, JavaType valueType) {

    try {
      return objectMapper.readValue(content, valueType);
    } catch (Exception e) {
      if (jsonOption.isThrowIfFailed()) {
        throw new FormatException("text readValue error", e);
      }
    }
    return null;
  }

  /**
   * @param content content
   * @return T
   * @title readValue
   * @description readValue
   * @author BiJi'an
   * @date 2024-01-01 23:30
   */
  @SuppressWarnings("unchecked")
  public <T> T readValue(String content) {
    try {
      return (T) objectMapper.readValue(content, Object.class);
    } catch (Exception e) {
      if (jsonOption.isThrowIfFailed()) {
        throw new FormatException("text readValue error", e);
      }
    }
    return null;
  }

  /**
   * @param content content
   * @title readValue
   * @description readValue
   * @author BiJi'an
   * @date 2024-01-01 23:50
   */

  @SuppressWarnings("unchecked")
  public <T> T readValue(byte[] content) {
    try {
      return (T) objectMapper.readValue(content, Object.class);
    } catch (Exception e) {
      if (jsonOption.isThrowIfFailed()) {
        throw new FormatException("text readValue error", e);
      }
    }
    return null;
  }


  /**
   * @param collectionClass collectionClass
   * @param elementClass    elementClass
   * @return com.fasterxml.jackson.databind.JavaType
   * @title constructParametricType
   * @description
   * @author BiJi'an
   * @date 2022-11-21 16:44
   */
  @SuppressWarnings("rawtypes")
  public JavaType constructCollectionType(
      Class<? extends Collection> collectionClass, Class<?> elementClass) {
    return objectMapper.getTypeFactory()
        .constructCollectionType(collectionClass, elementClass);
  }

}