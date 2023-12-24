package io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis;

import io.github.kylinhunter.commons.jdbc.binlog.bean.BinConfig;
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

    BinConfig binConfig = new BinConfig();
    binConfig.setServerId(999);
    byteBuffer = jdkRedisCodec.encodeValue(binConfig);
    Object decodeValue = jdkRedisCodec.decodeValue(byteBuffer);
    Assertions.assertEquals(binConfig, decodeValue);


  }
}