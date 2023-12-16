package io.github.kylinhunter.commons.jdbc.binlog.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.dao.entity.SavePoint;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp.RedisSavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.JdkRedisCodec;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.JsonRedisCodec;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.RedisConfig;
import io.github.kylinhunter.commons.jdbc.url.JdbcUrl;
import org.junit.jupiter.api.Test;

class RedisSavePointManagerTest {

  @Test
  void test1() {

    RedisConfig redisConfig = TestHelper.getRedisConfig();
    redisConfig.setRedisCodec(new JdkRedisCodec());

    RedisSavePointManager savePointManager = new RedisSavePointManager(redisConfig);
    savePointManager.setRecentBinLogKey("binlog_process_jdk");
    savePointManager.init(new JdbcUrl());

    savePointManager.reset();

    SavePoint savePoint = savePointManager.get();
    System.out.println(savePoint);
    assertNotNull(savePoint);

    SavePoint savePoint11 = new SavePoint("test1", 99);
    savePointManager.save(savePoint11);
    System.out.println(savePoint11);

    SavePoint savePoint12 = savePointManager.get();
    System.out.println(savePoint12);
    assertEquals(savePoint11, savePoint12);

    SavePoint savePoint21 = new SavePoint("test2", 100);
    savePointManager.save(savePoint21);
    System.out.println(savePoint21);

    SavePoint savePoint22 = savePointManager.get();
    System.out.println(savePoint22);
    assertNotEquals(savePoint11, savePoint22);
    assertEquals(savePoint21, savePoint22);

    savePointManager.reset();
  }

  @Test
  void test2() {

    RedisConfig redisConfig = TestHelper.getRedisConfig();
    redisConfig.setRedisCodec(new JsonRedisCodec());

    RedisSavePointManager savePointManager = new RedisSavePointManager(redisConfig);
    savePointManager.setRecentBinLogKey("binlog_process_json");
    savePointManager.init(new JdbcUrl());

    savePointManager.reset();

    SavePoint savePoint = savePointManager.get();
    System.out.println(savePoint);
    assertNotNull(savePoint);

    SavePoint savePoint11 = new SavePoint("test1", 99);
    savePointManager.save(savePoint11);
    System.out.println(savePoint11);

    SavePoint savePoint12 = savePointManager.get();
    System.out.println(savePoint12);
    assertEquals(savePoint11, savePoint12);

    SavePoint savePoint21 = new SavePoint("test2", 100);
    savePointManager.save(savePoint21);
    System.out.println(savePoint21);

    SavePoint savePoint22 = savePointManager.get();
    System.out.println(savePoint22);
    assertNotEquals(savePoint11, savePoint22);
    assertEquals(savePoint21, savePoint22);

    savePointManager.reset();
  }
}