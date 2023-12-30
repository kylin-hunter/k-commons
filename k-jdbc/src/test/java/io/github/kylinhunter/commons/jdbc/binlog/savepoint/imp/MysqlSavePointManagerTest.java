package io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.dao.entity.SavePoint;
import io.github.kylinhunter.commons.jdbc.binlog.dao.imp.MysqlSavePointDAO;
import io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta;
import io.github.kylinhunter.commons.jdbc.url.JdbcUrl;
import io.github.kylinhunter.commons.jdbc.utils.JdbcUtils;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MysqlSavePointManagerTest {

  @Test
  void test() throws SQLException {
    DataSource dataSource = TestHelper.mockDataSource();
    MysqlSavePointManager savePointManager = new MysqlSavePointManager(dataSource);
    MysqlSavePointDAO dao = Mockito.mock(MysqlSavePointDAO.class);
    DatabaseMeta databaseMeta = new DatabaseMeta();
    databaseMeta.setJdbcUrl(JdbcUtils.parse(TestHelper.MYSQL_JDBC_URL));
    Mockito.when(dao.getDBMetaData()).thenReturn(databaseMeta);

    ReflectUtils.setField(savePointManager, "mysqlSavePointDAO", dao);
    savePointManager.init(new JdbcUrl());

    savePointManager.reset();
    SavePoint defaultSavePoint = savePointManager.getDefaultSavePoint();

    Mockito.when(dao.get()).thenReturn(defaultSavePoint);

    SavePoint savePoint = savePointManager.get();
    System.out.println(savePoint);
    assertEquals(defaultSavePoint, savePoint);

    SavePoint savePoint11 = new SavePoint("test1", 99);
    savePointManager.save(savePoint11);
    System.out.println(savePoint11);

    Mockito.when(dao.get()).thenReturn(savePoint11);
    SavePoint savePoint12 = savePointManager.get();
    System.out.println(savePoint12);
    assertEquals(savePoint11, savePoint12);

    Mockito.when(dao.get()).thenReturn(defaultSavePoint);
    savePointManager.reset();
    savePoint = savePointManager.get();
    System.out.println(savePoint);
    assertEquals(defaultSavePoint, savePoint);
  }
}