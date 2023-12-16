package io.github.kylinhunter.commons.jdbc.binlog.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.dao.entity.SavePoint;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp.MysqlSavePointManager;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import io.github.kylinhunter.commons.jdbc.url.JdbcUrl;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;

class MysqlSavePointManagerTest {

  @Test
  void test() {
    String jdbcUrlStr = TestHelper.MYSQL_JDBC_URL;
    DataSource dataSource = DataSourceManager.createDataSource(jdbcUrlStr, "root", "root");

    SavePointManager savePointManager = new MysqlSavePointManager(dataSource);

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