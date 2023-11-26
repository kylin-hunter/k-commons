package io.github.kylinhunter.commons.jdbc.meta.table;

import io.github.kylinhunter.commons.jdbc.datasource.ExDataSource;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import java.sql.Connection;
import java.util.List;

public interface TableReader {

  /**
   * @param catalog   catalog
   * @param tableName tableName
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta
   * @title getTableMetaData
   * @description
   * @author BiJi'an
   * @date 2023-02-19 01:18
   */
  TableMeta getTableMetaData(String catalog, String tableName);

  /**
   * @param catalog   catalog
   * @param tableName tableName
   * @return java.util.List<io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta>
   * @title getColumnMetaData
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:42
   */
  List<TableMeta> getTableMetaDatas(String catalog, String tableName);

  /**
   * @param dataSource dataSource
   * @param catalog    catalog
   * @param tableName  tableName
   * @return java.util.List<io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta>
   * @title getColumnMetaData
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:42
   */
  List<TableMeta> getTableMetaDatas(
      ExDataSource dataSource, String catalog, String tableName);

  /**
   * @param connection connection
   * @param catalog    catalog
   * @param schema     schema
   * @param tableName  tableName
   * @return java.util.List<io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta>
   * @title getColumnMetaData
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:42
   */
  List<TableMeta> getTableMetaDatas(
      Connection connection, String catalog, String schema, String tableName);
}
