package io.github.kylinhunter.commons.jdbc.meta.cache;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.jdbc.meta.DatabaseMetaReader;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetas;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableId;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import io.github.kylinhunter.commons.jdbc.meta.column.ColumnReader;
import io.github.kylinhunter.commons.jdbc.meta.table.TableReader;
import java.util.Map;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-13 00:06
 */
@Slf4j
public class DatabaseMetaCache {

  protected final Map<Long, TableId> tables = MapUtils.newHashMap();
  protected final Map<TableId, TableMeta> tableMetas = MapUtils.newHashMap();
  protected final Map<TableId, ColumnMetas> allColumnMetas = MapUtils.newHashMap();
  protected DatabaseMetaReader databaseMetaReader;
  private final TableReader tableReader;
  private final ColumnReader columnReader;

  protected DataSource dataSource;

  public DatabaseMetaCache(DataSource dataSource) {
    this.dataSource = dataSource;
    this.databaseMetaReader = new DatabaseMetaReader(dataSource);
    this.tableReader = this.databaseMetaReader.getTableReader();
    this.columnReader = this.databaseMetaReader.getColumnReader();
  }

  /**
   * @param id id
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.TableId
   * @title get
   * @description get
   * @author BiJi'an
   * @date 2023-12-13 00:16
   */
  public TableId get(long id) {
    return tables.get(id);
  }

  /**
   * @param tableId     tableId
   * @param tableName   tableName
   * @param forceUpdate forceUpdate
   * @title addTable
   * @description addTable
   * @author BiJi'an
   * @date 2023-12-09 23:27
   */
  public void updateTableCache(
      Long tableId, String database, String tableName, boolean forceUpdate) {
    TableId tableKey = new TableId(database, tableName);
    tables.put(tableId, tableKey);
    updateTableCache(tableKey, forceUpdate);
  }

  /**
   * @param tableId     tableKey
   * @param forceUpdate forceUpdate
   * @title updateTableMeta
   * @description updateTableMeta
   * @author BiJi'an
   * @date 2023-12-09 23:29
   */
  public void updateTableCache(TableId tableId, boolean forceUpdate) {

    TableMeta tableMeta = tableMetas.get(tableId);
    if (tableMeta == null || forceUpdate) {
      tableMeta =
          tableReader.getTableMetaData(this.dataSource, tableId.getDatabase(), tableId.getName());
      if (tableMeta != null) {
        tableMetas.put(tableId, tableMeta);
        log.info("############# updateTableMeta={}", tableMeta);
      }
    }

    ColumnMetas columnMetas = this.allColumnMetas.get(tableId);
    if (columnMetas == null || forceUpdate) {
      columnMetas =
          columnReader.getColumnMetaData(this.dataSource, tableId.getDatabase(), tableId.getName());
      if (columnMetas != null) {
        this.allColumnMetas.put(tableId, columnMetas);
        log.info("############# updateColumnMeta={}", columnMetas);
      }
    }
  }

  /**
   * @param tableId tableId
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetas
   * @title getColumnMetas
   * @description getColumnMetas
   * @author BiJi'an
   * @date 2023-12-13 00:57
   */
  public ColumnMetas getColumnMetas(TableId tableId) {
    return this.allColumnMetas.get(tableId);

  }


}