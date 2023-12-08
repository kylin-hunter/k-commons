package io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.dao.entity.SavePoint;
import io.github.kylinhunter.commons.jdbc.config.url.JdbcUrl;
import org.junit.jupiter.api.Test;

class MysqlSavePointManagerTest {

  @Test
  void test() {

    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(
        "jdbc:mysql://localhost:3306/kp?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai");
    hikariConfig.setUsername("root");
    hikariConfig.setPassword("root");
    HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
    SavePointManager savePointManager = new MysqlSavePointManager(hikariDataSource);

    JdbcUrl jdbcUrl = new JdbcUrl();
//    jdbcUrl.setHost("localhost");
//    jdbcUrl.setPort(3306);
    savePointManager.init(jdbcUrl);

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