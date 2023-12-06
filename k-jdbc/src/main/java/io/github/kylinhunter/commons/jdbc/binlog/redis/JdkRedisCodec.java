package io.github.kylinhunter.commons.jdbc.binlog.redis;

import io.github.kylinhunter.commons.exception.embed.GeneralException;
import io.github.kylinhunter.commons.serialize.ObjectBytesSerializer;
import io.lettuce.core.codec.RedisCodec;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-07 00:38
 */
public class JdkRedisCodec implements RedisCodec<String, Object> {

  private final Charset charset = StandardCharsets.UTF_8;

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
      byte[] bytes = ObjectBytesSerializer.serialize(value);
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
      return ObjectBytesSerializer.deserialize(array);

    } catch (Exception e) {
      throw new GeneralException("decodeValue error", e);
    }
  }

}