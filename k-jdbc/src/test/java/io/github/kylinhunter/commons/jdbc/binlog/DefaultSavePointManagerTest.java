package io.github.kylinhunter.commons.jdbc.binlog;

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

  }
}