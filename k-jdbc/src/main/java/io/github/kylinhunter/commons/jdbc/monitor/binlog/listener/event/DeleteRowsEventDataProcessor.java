package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event;

import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableId;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.BinLogEventListener;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.Context;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-13 00:23
 */
@Slf4j
public class DeleteRowsEventDataProcessor extends BasicEventProcessor {

  /**
   * @param data data
   * @title processEventRotate
   * @description processEventRotate
   * @author BiJi'an
   * @date 2023-12-10 01:07
   */
  public void process(BinLogEventListener binLogEventListener, EventData data,
      Context context) {
    if (data instanceof DeleteRowsEventData) {
      DeleteRowsEventData eventData = (DeleteRowsEventData) data;
      log.info("DeleteRowsEventData={}", eventData);
      TableId tableId = databaseMetaCache.get(eventData.getTableId());
      deleteDataRecord(tableId, eventData, context);
    }

  }

  /**
   * @param tableId   tableKey
   * @param eventData eventData
   * @title deleteEvent
   * @description deleteEvent
   * @author BiJi'an
   * @date 2023-12-10 01:08
   */
  protected void deleteDataRecord(TableId tableId, DeleteRowsEventData eventData, Context context) {
  }


}