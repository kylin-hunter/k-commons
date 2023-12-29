package io.github.kylinhunter.commons.jdbc.binlog.redis;

import io.github.kylinhunter.commons.date.DateUtils;
import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.dao.entity.SavePoint;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.ClusterRedisConfig;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.ClusterRedisExecutor;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.RedisExecutor;

public class TestClusterRedisExecutor {

  public static void main(String[] args) {
    ClusterRedisConfig clusterRedisConfig = TestHelper.getClusterRedisConfig();
    RedisExecutor executor = new ClusterRedisExecutor(clusterRedisConfig);

    executor.set("TestClusterRedisExecutor1", DateUtils.formatNow());

    executor.set("TestClusterRedisExecutor2", new SavePoint("a", 100));

    String value = executor.get("TestClusterRedisExecutor1");
    SavePoint savePoint = executor.get("TestClusterRedisExecutor2");

    System.out.println("value: " + value);
    System.out.println("savePoint: " + savePoint);
  }
}