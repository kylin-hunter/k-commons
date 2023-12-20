package io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.kylinhunter.commons.jdbc.binlog.dao.entity.SavePoint;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.RedisExecutor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RedisSavePointManagerTest {

  @Test
  void test() {

    RedisExecutor redisExecutor = Mockito.mock(RedisExecutor.class);
    RedisSavePointManager savePointManager = new RedisSavePointManager(redisExecutor);
    savePointManager.setRecentBinLogKey("binlog_process_jdk");
    savePointManager.init(null);
    savePointManager.reset();
    SavePoint defaultSavePoint = savePointManager.getDefaultSavePoint();

    Mockito.when(redisExecutor.get("binlog_process_jdk")).thenReturn(defaultSavePoint);

    SavePoint savePoint = savePointManager.get();
    System.out.println(savePoint);
    assertEquals(defaultSavePoint, savePoint);

    SavePoint savePoint11 = new SavePoint("test1", 99);
    savePointManager.save(savePoint11);
    System.out.println(savePoint11);

    Mockito.when(redisExecutor.get("binlog_process_jdk")).thenReturn(savePoint11);
    SavePoint savePoint12 = savePointManager.get();
    System.out.println(savePoint12);
    assertEquals(savePoint11, savePoint12);

    Mockito.when(redisExecutor.get("binlog_process_jdk")).thenReturn(defaultSavePoint);
    savePointManager.reset();
    savePoint = savePointManager.get();
    System.out.println(savePoint);
    assertEquals(defaultSavePoint, savePoint);
  }

}