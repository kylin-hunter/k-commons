package io.github.kylinhunter.commons.jdbc.dao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.meta.DatabaseMetaReader;
import io.github.kylinhunter.commons.jdbc.meta.table.TableReader;
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
    DatabaseMetaReader databaseMetaReader = Mockito.mock(DatabaseMetaReader.class);
    TableReader rableReader = Mockito.mock(TableReader.class);
    when(databaseMetaReader.getTableReader()).thenReturn(rableReader);

    when(rableReader.exist(any(), any(), eq(TestHelper.TEST_TABLE_TMP))).thenReturn(true);
    when(rableReader.exist(any(), any(), eq(TestHelper.TEST_TABLE_TMP + 1))).thenReturn(false);

    AbsctractBasicDAO basicDAO = new AbsctractBasicDAO(dataSource, false);
    ReflectUtils.setField(basicDAO, "sqlExecutor", sqlExecutor);
    ReflectUtils.setField(basicDAO, "databaseMetaReader", databaseMetaReader);

    String tableName = TestHelper.TEST_TABLE_TMP;
    basicDAO.dropTable(tableName);
    basicDAO.ensureTableExists(tableName, "create table " + tableName + " (id int)");
    basicDAO.ensureTableExists(tableName + 1, "create table " + tableName + " (id int)");


  }
}