package io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis;

import io.github.kylinhunter.commons.jdbc.binlog.dao.entity.SavePoint;
import java.nio.ByteBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JsonRedisCodecTest {

  @Test
  void test() {

    JsonRedisCodec jsonRedisCodec = new JsonRedisCodec();

    ByteBuffer byteBuffer = jsonRedisCodec.encodeKey("123");
    String key = jsonRedisCodec.decodeKey(byteBuffer);
    Assertions.assertEquals("123", key);

    SavePoint savePoint = new SavePoint();
    savePoint.setPosition(999);
    byteBuffer = jsonRedisCodec.encodeValue(savePoint);
    SavePoint decodeValue = (SavePoint) jsonRedisCodec.decodeValue(byteBuffer);
    Assertions.assertEquals(999, decodeValue.getPosition());
  }
}