package io.github.kylinhunter.commons.jdbc.meta;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta;
import io.github.kylinhunter.commons.jdbc.meta.column.ColumnReader;
import io.github.kylinhunter.commons.jdbc.meta.column.imp.MysqlColumnReader;
import io.github.kylinhunter.commons.jdbc.meta.column.imp.OracleColumnReader;
import io.github.kylinhunter.commons.jdbc.meta.column.imp.SqlServerColumnReader;
import io.github.kylinhunter.commons.jdbc.meta.table.MysqlTableReader;
import io.github.kylinhunter.commons.jdbc.meta.table.OracleTableReader;
import io.github.kylinhunter.commons.jdbc.meta.table.SqlServerTableReader;
import io.github.kylinhunter.commons.jdbc.meta.table.TableReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DatabaseMetaReaderTest {


  @Test
  void test() {
    DatabaseMetaReader databaseMetaReader = new DatabaseMetaReader();
    DatabaseMeta databaseMeta = databaseMetaReader.getDBMetaData();
    System.out.println(databaseMeta);
    Assertions.assertEquals(DbType.MYSQL, databaseMeta.getDbType());

    ColumnReader columnReader = databaseMetaReader.getColumnReader();
    Assertions.assertTrue(MysqlColumnReader.class.isAssignableFrom(columnReader.getClass()));

    TableReader tableReader = databaseMetaReader.getTableReader();
    Assertions.assertTrue(MysqlTableReader.class.isAssignableFrom(tableReader.getClass()));

  }

  @Test
  void testOracle() throws SQLException {

    DatabaseMetaData databaseMetaDataMock = Mockito.mock(DatabaseMetaData.class);

    Mockito.when(databaseMetaDataMock.getURL()).thenReturn(TestHelper.ORACLE_JDBC_URL);
    Mockito.when(databaseMetaDataMock.getDatabaseProductName()).thenReturn("oracle");
    Mockito.when(databaseMetaDataMock.getDatabaseProductVersion()).thenReturn("11.0.2");
    Mockito.when(databaseMetaDataMock.getDriverName()).thenReturn("oracle Connector/J");

    Connection connectionMock = Mockito.mock(Connection.class);
    Mockito.when(connectionMock.getMetaData()).thenReturn(databaseMetaDataMock);

    DataSource dataSource = Mockito.mock(DataSource.class);
    Mockito.when(dataSource.getConnection()).thenReturn(connectionMock);

    DatabaseMetaReader databaseMetaReader = new DatabaseMetaReader(dataSource);
    DatabaseMeta databaseMeta = databaseMetaReader.getDBMetaData();
    System.out.println(databaseMeta);
    Assertions.assertEquals(DbType.ORACLE, databaseMeta.getDbType());

    ColumnReader columnReader = databaseMetaReader.getColumnReader();
    Assertions.assertTrue(OracleColumnReader.class.isAssignableFrom(columnReader.getClass()));

    TableReader tableReader = databaseMetaReader.getTableReader();
    Assertions.assertTrue(OracleTableReader.class.isAssignableFrom(tableReader.getClass()));

  }

  @Test
  void testSqlServer() throws SQLException {

    DatabaseMetaData databaseMetaDataMock = Mockito.mock(DatabaseMetaData.class);

    Mockito.when(databaseMetaDataMock.getURL()).thenReturn(TestHelper.SQLSERVER_JDBC_URL);
    Mockito.when(databaseMetaDataMock.getDatabaseProductName()).thenReturn("sql-server");
    Mockito.when(databaseMetaDataMock.getDatabaseProductVersion()).thenReturn("2012");
    Mockito.when(databaseMetaDataMock.getDriverName()).thenReturn("sql-server Connector/J");

    Connection connectionMock = Mockito.mock(Connection.class);
    Mockito.when(connectionMock.getMetaData()).thenReturn(databaseMetaDataMock);

    DataSource dataSource = Mockito.mock(DataSource.class);
    Mockito.when(dataSource.getConnection()).thenReturn(connectionMock);

    DatabaseMetaReader databaseMetaReader = new DatabaseMetaReader(dataSource);
    DatabaseMeta databaseMeta = databaseMetaReader.getDBMetaData();
    System.out.println(databaseMeta);
    Assertions.assertEquals(DbType.SQL_SERVER, databaseMeta.getDbType());

    ColumnReader columnReader = databaseMetaReader.getColumnReader();
    Assertions.assertTrue(SqlServerColumnReader.class.isAssignableFrom(columnReader.getClass()));

    TableReader tableReader = databaseMetaReader.getTableReader();
    Assertions.assertTrue(SqlServerTableReader.class.isAssignableFrom(tableReader.getClass()));

  }


}
