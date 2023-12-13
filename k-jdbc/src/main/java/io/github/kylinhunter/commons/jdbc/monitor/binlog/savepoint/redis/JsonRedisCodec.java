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
package io.github.kylinhunter.commons.jdbc.monitor.binlog.savepoint.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import io.github.kylinhunter.commons.exception.embed.GeneralException;
import io.lettuce.core.codec.RedisCodec;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-03 00:38
 */
public class JsonRedisCodec implements RedisCodec<String, Object> {

  private final Charset charset = StandardCharsets.UTF_8;
  private final ObjectMapper objectMapper = createObjectMapper();

  @Override
  public ByteBuffer encodeKey(String key) {
    return charset.encode(key);
  }

  @Override
  public String decodeKey(ByteBuffer bytes) {
    return charset.decode(bytes).toString();
  }

  @Override
  public ByteBuffer encodeValue(Object value) {
    try {
      byte[] bytes = objectMapper.writeValueAsBytes(value);
      return ByteBuffer.wrap(bytes);
    } catch (Exception e) {
      throw new GeneralException("encodeValue error", e);
    }
  }

  @Override
  public Object decodeValue(ByteBuffer bytes) {
    try {
      byte[] array = new byte[bytes.remaining()];
      bytes.get(array);
      return objectMapper.readValue(array, Object.class);

    } catch (Exception e) {
      throw new GeneralException("decodeValue error", e);
    }
  }

  /**
   * @return com.fasterxml.jackson.databind.ObjectMapper
   * @title createObjectMapper
   * @description createObjectMapper
   * @author BiJi'an
   * @date 2023-12-03 02:11
   */
  public ObjectMapper createObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    objectMapper.activateDefaultTyping(
        LaissezFaireSubTypeValidator.instance,
        ObjectMapper.DefaultTyping.NON_FINAL,
        JsonTypeInfo.As.WRAPPER_ARRAY);
    return objectMapper;
  }
}
