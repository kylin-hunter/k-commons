package io.github.kylinhunter.commons.jdbc.binlog.redis;

import io.github.kylinhunter.commons.date.DateUtils;
import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.RedisExecutor;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.SingleRedisConfig;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.SingleRedisExecutor;

public class TestSingleRedisExecutor {

  public static void main(String[] args) {
    SingleRedisConfig singleRedisConfig = TestHelper.getSingleRedisConfig();
    RedisExecutor executor = new SingleRedisExecutor(singleRedisConfig);

    executor.set("RedisExecutorTest", DateUtils.formatNow());

    String v = executor.get("RedisExecutorTest");
    System.out.println("value: " + v);
  }
}