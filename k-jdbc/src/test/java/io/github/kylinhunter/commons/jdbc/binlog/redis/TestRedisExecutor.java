package io.github.kylinhunter.commons.jdbc.binlog.redis;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.RedisConfig;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.RedisExecutor;

public class TestRedisExecutor {

  public static void main(String[] args) {
    RedisConfig redisConfig = TestHelper.getRedisConfig();
    RedisExecutor executor = new RedisExecutor(redisConfig);

    executor.set("RedisExecutorTest", "1");

    String v = executor.get("RedisExecutorTest");
    System.out.println("value: " + v);
  }
}