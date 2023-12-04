package io.github.kylinhunter.commons.jdbc.binlog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.kylinhunter.commons.jdbc.binlog.savepoint.DefaultSavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.bean.SavePoint;
import org.junit.jupiter.api.Test;

class DefaultSavePointManagerTest {

  @Test
  void test() {
    SavePointManager savePointManager = new DefaultSavePointManager();
    savePointManager.init();

    savePointManager.delete("test1");
    savePointManager.delete("test2");

    SavePoint savePoint = new SavePoint("test1", 99);
    savePointManager.saveOrUpdate(savePoint);
    System.out.println(savePoint);

    savePoint = savePointManager.get("test1");
    System.out.println(savePoint);
    System.out.println(savePointManager.getLatest());

    savePoint = new SavePoint("test2", 100);
    savePointManager.saveOrUpdate(savePoint);
    System.out.println(savePoint);

    savePoint = savePointManager.get("test2");
    System.out.println(savePoint);

    savePoint = new SavePoint("test2", 101);
    savePointManager.saveOrUpdate(savePoint);
    System.out.println(savePoint);

    savePoint = savePointManager.get("test2");
    System.out.println(savePoint);
    System.out.println(savePointManager.getLatest());

    assertEquals(savePointManager.getLatest().getName(), "test2");
    savePointManager.delete("test1");
    savePointManager.delete("test2");

  }
}