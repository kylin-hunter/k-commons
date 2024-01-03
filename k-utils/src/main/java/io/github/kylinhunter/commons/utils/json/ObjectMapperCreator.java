/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.utils.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.github.kylinhunter.commons.date.DatePatterns;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-21 19:09
 */
public class ObjectMapperCreator {


  public static ObjectMapper create(JsonOption jsonOption) {
    ObjectMapper objectMapper = createDefaultObjectMapper();
    if (jsonOption.isSnake()) {
      objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    }
    if (jsonOption.isPretty()) {
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    if (jsonOption.isAutoType()) {
      objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
      objectMapper.activateDefaultTyping(
          LaissezFaireSubTypeValidator.instance,
          ObjectMapper.DefaultTyping.NON_FINAL,
          JsonTypeInfo.As.WRAPPER_ARRAY);
    }
    return objectMapper;
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
    javaTimeModule.addSerializer(
        LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
    javaTimeModule.addDeserializer(
        LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
    objectMapper.registerModule(javaTimeModule);

    return objectMapper;
  }

}
