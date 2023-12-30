package io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.dao.entity.SavePoint;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.RedisExecutor;

public class TestRedisSavePointManager {

  public static void main(String[] args) {
    test1();
    test2();

  }

  static void test1() {
    RedisExecutor redisExecutor = TestHelper.getJsonSingleRedisExecutor();
    RedisSavePointManager savePointManager = new RedisSavePointManager(redisExecutor);
    savePointManager.setRecentBinLogKey("binlog_process_json");
    test(savePointManager);
  }

  static void test2() {
    RedisExecutor redisExecutor = TestHelper.getJsonSingleRedisExecutor();
    RedisSavePointManager savePointManager = new RedisSavePointManager(redisExecutor);
    savePointManager.setRecentBinLogKey("binlog_process_jdk");
    test(savePointManager);
  }

  static void test(SavePointManager savePointManager) {
    savePointManager.init(null);
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