package io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.kylinhunter.commons.jdbc.binlog.savepoint.bean.RedisConfig;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.bean.SavePoint;
import org.junit.jupiter.api.Test;

class RedisSavePointManagerTest {

  @Test
  void test() {

    RedisConfig redisConfig = new RedisConfig();
    redisConfig.setHost("127.0.0.1");
    redisConfig.setPort(6379);
    redisConfig.setPassword("123456");

    RedisSavePointManager savePointManager = new RedisSavePointManager(redisConfig);

    savePointManager.init();

    savePointManager.reset();

    SavePoint savePoint = savePointManager.getLatest();
    System.out.println(savePoint);
    assertNotNull(savePoint);

    SavePoint savePoint11 = new SavePoint("test1", 99);
    savePointManager.save(savePoint11);
    System.out.println(savePoint11);

    SavePoint savePoint12 = savePointManager.getLatest();
    System.out.println(savePoint12);
    assertEquals(savePoint11, savePoint12);

    SavePoint savePoint21 = new SavePoint("test2", 100);
    savePointManager.save(savePoint21);
    System.out.println(savePoint21);

    SavePoint savePoint22 = savePointManager.getLatest();
    System.out.println(savePoint22);
    assertNotEquals(savePoint11, savePoint22);
    assertEquals(savePoint21, savePoint22);

    savePointManager.reset();
  }
}