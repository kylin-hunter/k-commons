package io.github.kylinhunter.commons.jdbc.meta;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.meta.column.ColumnReader;
import io.github.kylinhunter.commons.jdbc.meta.table.TableReader;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-26 23:38
 */
public class MetaReaderFactory {

  /**
   * @param dbType dbType
   * @return io.github.kylinhunter.commons.jdbc.meta.table.TableMetaReader
   * @throws
   * @title getTableMetaReader
   * @description getTableMetaReader
   * @author BiJi'an
   * @date 2023-11-26 23:59
   */
  public static TableReader getTableMetaReader(DbType dbType) {
    return CF.get(dbType.getPrefix() + "TableReader");
  }

  /**
   * @param dbType dbType
   * @return io.github.kylinhunter.commons.jdbc.meta.column.ColumnMetaReader
   * @throws
   * @title getColumnMetaReader
   * @description getColumnMetaReader
   * @author BiJi'an
   * @date 2023-11-26 23:59
   */
  public static ColumnReader getColumnMetaReader(DbType dbType) {
    return CF.get(dbType.getPrefix() + "ColumnReader");
  }

}