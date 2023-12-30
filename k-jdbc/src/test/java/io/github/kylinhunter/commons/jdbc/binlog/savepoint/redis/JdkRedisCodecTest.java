package io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis;

import io.github.kylinhunter.commons.jdbc.binlog.dao.entity.SavePoint;
import java.nio.ByteBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JdkRedisCodecTest {

  @Test
  void test() {
    JdkRedisCodec jdkRedisCodec = new JdkRedisCodec();

    ByteBuffer byteBuffer = jdkRedisCodec.encodeKey("123");
    String key = jdkRedisCodec.decodeKey(byteBuffer);
    Assertions.assertEquals("123", key);

    SavePoint savePoint = new SavePoint();
    savePoint.setName("name");
    savePoint.setPosition(100L);
    byteBuffer = jdkRedisCodec.encodeValue(savePoint);
    Object decodeValue = jdkRedisCodec.decodeValue(byteBuffer);
    Assertions.assertEquals(savePoint, decodeValue);


  }

}