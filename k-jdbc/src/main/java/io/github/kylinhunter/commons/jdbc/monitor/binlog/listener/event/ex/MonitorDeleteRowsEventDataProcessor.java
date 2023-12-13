package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.ex;

import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetas;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableId;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.Context;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.DeleteRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.dao.constant.RowOP;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableMonitorTaskManager;
import java.io.Serializable;
import java.util.List;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-13 00:48
 */
public class MonitorDeleteRowsEventDataProcessor extends DeleteRowsEventDataProcessor {

  private TableMonitorTaskManager tableMonitorTaskManager;
  @Setter
  private String destination = "k_table_monitor_binlog_task";
  @Setter
  private TableId targetTableId;
  @Setter
  private String targetTablePK;

  @Override
  protected void deleteDataRecord(TableId tableId, DeleteRowsEventData eventData, Context context) {
    if (!this.targetTableId.equals(tableId)) {
      return;
    }

    ColumnMeta pkColumnMeta = getPkColumnMeta(context);
    if (pkColumnMeta == null) {
      return;
    }
    List<Serializable[]> rows = eventData.getRows();
    for (Serializable[] row : rows) {
      processScanRecord(row, pkColumnMeta);
    }
  }

  /**
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta
   * @title getPkColumnMeta
   * @description getPkColumnMeta
   * @author BiJi'an
   * @date 2023-12-12 01:18
   */
  private ColumnMeta getPkColumnMeta(Context context) {
    ColumnMetas columnMetas = databaseMetaCache.getColumnMetas(targetTableId);
    if (columnMetas != null) {
      return columnMetas.getByName(targetTablePK);
    }
    return null;
  }

  /**
   * @param row          row
   * @param pkColumnMeta pkColumnMeta
   * @title processScanRecord
   * @description processScanRecord
   * @author BiJi'an
   * @date 2023-12-12 01:42
   */
  private void processScanRecord(Serializable[] row, ColumnMeta pkColumnMeta) {
    if (pkColumnMeta.getPos() < row.length) {
      tableMonitorTaskManager.saveOrUpdate(destination, targetTableId.getName(),
          String.valueOf(row[pkColumnMeta.getPos()]), RowOP.DELETE);
    }
  }

}