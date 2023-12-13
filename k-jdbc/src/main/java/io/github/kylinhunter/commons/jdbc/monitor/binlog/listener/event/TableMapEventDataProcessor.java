package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event;

import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.BinLogEventListener;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.Context;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-13 00:23
 */
@Slf4j
public class TableMapEventDataProcessor extends BasicEventProcessor {

  /**
   * @param data data
   * @title processEventRotate
   * @description processEventRotate
   * @author BiJi'an
   * @date 2023-12-10 01:07
   */
  public void process(BinLogEventListener binLogEventListener, EventData data,
      Context context) {
    if (data instanceof TableMapEventData) {
      TableMapEventData eventData = (TableMapEventData) data;
      String database = eventData.getDatabase();
      long tableId = eventData.getTableId();
      String table = eventData.getTable();
      log.info("table={}/{}/{}", database, tableId, table);
      databaseMetaCache.updateTableCache(tableId, database, table, false);
    }
  }
}