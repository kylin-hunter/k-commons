package io.github.kylinhunter.commons.generator.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import io.github.kylinhunter.commons.generator.TestHelper;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.mockito.Mockito;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-10 00:26
 */
public class TestDataSourceHelper {

  /**
   * @return javax.sql.DataSource
   * @title defaultDataSource
   * @description defaultDataSource
   * @author BiJi'an
   * @date 2023-12-17 20:38
   */
  public static DataSource defaultDataSource() {
    DataSourceManager dataSourceManager = new DataSourceManager();
    dataSourceManager.init();
    return dataSourceManager.get();
  }

  /**
   * @return javax.sql.DataSource
   * @title mockDataSource
   * @description mockDataSource
   * @author BiJi'an
   * @date 2023-12-17 20:38
   */

  public static DataSource mockDataSource() throws SQLException {
    DataSource dataSource = Mockito.mock(DataSource.class);

    Connection connection = Mockito.mock(Connection.class);
    Mockito.when(dataSource.getConnection()).thenReturn(connection);

    DatabaseMetaData databaseMetaData = Mockito.mock(DatabaseMetaData.class);
    Mockito.when(connection.getMetaData()).thenReturn(databaseMetaData);

    Mockito.when(databaseMetaData.getURL()).thenReturn(TestHelper.MYSQL_JDBC_URL);
    Mockito.when(databaseMetaData.getDatabaseProductName()).thenReturn("mysql");
    Mockito.when(databaseMetaData.getDatabaseProductVersion()).thenReturn("8.0");
    Mockito.when(databaseMetaData.getDriverName()).thenReturn("mysql Connector/J");
    mockTableMetadata(databaseMetaData);
    mockColumnMetadata(databaseMetaData);
    return dataSource;
  }

  /**
   * @param databaseMetaData databaseMetaData
   * @title mockTableMetadata
   * @description mockTableMetadata
   * @author BiJi'an
   * @date 2023-12-17 19:19
   */
  private static void mockTableMetadata(DatabaseMetaData databaseMetaData) throws SQLException {
    mockTableMetadata(databaseMetaData, TestHelper.TABLE_USER);
    mockTableMetadata(databaseMetaData, TestHelper.TABLE_ROLE);


  }

  private static void mockTableMetadata(DatabaseMetaData databaseMetaData, String tableName)
      throws SQLException {
    ResultSet getTablesResultSet1 = Mockito.mock(ResultSet.class);

    Mockito.when(databaseMetaData.getTables(any(), eq(null), eq(tableName),
        eq(new String[]{"TABLE"}))).thenReturn(getTablesResultSet1);

    Mockito.when(getTablesResultSet1.next()).thenReturn(true).thenReturn(false);

    ResultSetMetaData tablesMetadata = Mockito.mock(ResultSetMetaData.class);
    Mockito.when(getTablesResultSet1.getMetaData()).thenReturn(tablesMetadata);
    Mockito.when(tablesMetadata.getColumnCount()).thenReturn(3);
    Mockito.when(tablesMetadata.getColumnName(1)).thenReturn("TABLE_NAME");
    Mockito.when(tablesMetadata.getColumnName(2)).thenReturn("REMARKS");
    Mockito.when(tablesMetadata.getColumnName(3)).thenReturn("TABLE_CAT");

    Mockito.when(getTablesResultSet1.getObject(1)).thenReturn(tableName);
    Mockito.when(getTablesResultSet1.getObject(2)).thenReturn("remarks");
    Mockito.when(getTablesResultSet1.getObject(3)).thenReturn("kp");
  }


  /**
   * @param databaseMetaData databaseMetaData
   * @title mockColumnMetadata
   * @description mockColumnMetadata
   * @author BiJi'an
   * @date 2023-12-17 19:22
   */
  private static void mockColumnMetadata(DatabaseMetaData databaseMetaData) throws SQLException {
    mockColumnMetadata(databaseMetaData, TestHelper.TABLE_USER);
    mockColumnMetadata(databaseMetaData, TestHelper.TABLE_ROLE);
  }

  /**
   * @param databaseMetaData databaseMetaData
   * @param tableName        tableName
   * @title mockColumnMetadata
   * @description mockColumnMetadata
   * @author BiJi'an
   * @date 2023-12-17 15:55
   */

  private static void mockColumnMetadata(DatabaseMetaData databaseMetaData, String tableName)
      throws SQLException {
    ResultSet getTablesResultSet1 = Mockito.mock(ResultSet.class);

    Mockito.when(databaseMetaData.getColumns(any(), eq(null), eq(tableName),
        eq(null))).thenReturn(getTablesResultSet1);

    Mockito.when(getTablesResultSet1.next()).thenReturn(true).thenReturn(false);

    ResultSetMetaData tablesMetadata = Mockito.mock(ResultSetMetaData.class);
    Mockito.when(getTablesResultSet1.getMetaData()).thenReturn(tablesMetadata);
    Mockito.when(tablesMetadata.getColumnCount()).thenReturn(9);
    Mockito.when(tablesMetadata.getColumnName(1)).thenReturn("TABLE_NAME");
    Mockito.when(tablesMetadata.getColumnName(2)).thenReturn("COLUMN_NAME");
    Mockito.when(tablesMetadata.getColumnName(3)).thenReturn("DATA_TYPE");
    Mockito.when(tablesMetadata.getColumnName(4)).thenReturn("TYPE_NAME");
    Mockito.when(tablesMetadata.getColumnName(5)).thenReturn("COLUMN_SIZE");
    Mockito.when(tablesMetadata.getColumnName(6)).thenReturn("DECIMAL_DIGITS");
    Mockito.when(tablesMetadata.getColumnName(7)).thenReturn("IS_AUTOINCREMENT");
    Mockito.when(tablesMetadata.getColumnName(8)).thenReturn("NULLABLE");
    Mockito.when(tablesMetadata.getColumnName(9)).thenReturn("REMARKS");

    Mockito.when(getTablesResultSet1.getObject(1)).thenReturn(tableName);
    Mockito.when(getTablesResultSet1.getObject(2)).thenReturn("id");
    Mockito.when(getTablesResultSet1.getObject(3)).thenReturn("1");
    Mockito.when(getTablesResultSet1.getObject(4)).thenReturn("INT");
    Mockito.when(getTablesResultSet1.getObject(5)).thenReturn("10");
    Mockito.when(getTablesResultSet1.getObject(6)).thenReturn("2");
    Mockito.when(getTablesResultSet1.getObject(7)).thenReturn("true");
    Mockito.when(getTablesResultSet1.getObject(8)).thenReturn("true");
    Mockito.when(getTablesResultSet1.getObject(9)).thenReturn("remarks");
  }
}


