package io.github.kylinhunter.commons.jdbc.dao;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AbsctractBasicDAOTest {

  @Test
  void ensureTableExists() throws SQLException {
    SqlExecutor sqlExecutor = Mockito.mock(SqlExecutor.class);
    DataSource dataSource = TestHelper.mockDataSource();
    AbsctractBasicDAO basicDAO = new AbsctractBasicDAO(dataSource, false);
    ReflectUtils.setField(basicDAO, "sqlExecutor", sqlExecutor);
    String tableName = TestHelper.TEST_TABLE_TMP;
    basicDAO.dropTable(tableName);
    basicDAO.ensureTableExists(tableName, "create table " + tableName + " (id int)");

    basicDAO.ensureTableExists(tableName + 2, "create table " + tableName + " (id int)");

  }
}