package io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis;

import io.github.kylinhunter.commons.jdbc.binlog.bean.BinLogConfig;
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

    BinLogConfig binLogConfig = new BinLogConfig();
    binLogConfig.setServerId(999);
    byteBuffer = jdkRedisCodec.encodeValue(binLogConfig);
    Object decodeValue = jdkRedisCodec.decodeValue(byteBuffer);
    Assertions.assertEquals(binLogConfig, decodeValue);


  }
}