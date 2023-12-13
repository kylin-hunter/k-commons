package io.github.kylinhunter.commons.jdbc.monitor.binlog.redis;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.savepoint.redis.RedisConfig;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.savepoint.redis.RedisExecutor;
import org.junit.jupiter.api.Test;

class RedisExecutorTest {

  @Test
  void test() {
    RedisConfig jedisConfig = TestHelper.getRedisConfig();
    RedisExecutor executor = new RedisExecutor(jedisConfig);

    executor.set("RedisExecutorTest", "1");

    String v = executor.get("RedisExecutorTest");
    System.out.println("value: " + v);
  }
}