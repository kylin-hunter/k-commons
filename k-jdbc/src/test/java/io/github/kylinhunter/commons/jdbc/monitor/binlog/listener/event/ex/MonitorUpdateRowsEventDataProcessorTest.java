package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.ex;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.google.common.collect.Lists;
import io.github.kylinhunter.commons.jdbc.binlog.listener.Context;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.cache.DatabaseMetaCache;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.MonitorTable;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.MonitorUpdateRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.dao.constant.RowOP;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableMonitorTaskManager;
import java.io.Serializable;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MonitorUpdateRowsEventDataProcessorTest {

  @Test
  void updateDataRecord() {

    MonitorTable monitorTable = new MonitorTable();
    monitorTable.setDatabase("");
    monitorTable.setName("");
    monitorTable.setTablePkName("");

    DatabaseMetaCache databaseMetaCache = Mockito.mock(DatabaseMetaCache.class);
    ColumnMeta columnMeta = new ColumnMeta();
    Mockito.when(databaseMetaCache.getPkColumnMeta(anyLong(), anyString(),
        anyString(), anyString())).thenReturn(columnMeta);

    TableMonitorTaskManager tableMonitorTaskManager = Mockito.mock(TableMonitorTaskManager.class);
    MonitorUpdateRowsEventDataProcessor processor = new MonitorUpdateRowsEventDataProcessor(
        tableMonitorTaskManager,
        monitorTable);
    processor.setDatabaseMetaCache(databaseMetaCache);

    UpdateRowsEventData eventData = Mockito.mock(UpdateRowsEventData.class);

    Map.Entry<Serializable[], Serializable[]> entry = new Map.Entry<>() {
      @Override
      public Serializable[] getKey() {
        return new Serializable[]{"a1", "a2"};
      }

      @Override
      public Serializable[] getValue() {
        return new Serializable[]{"b1", "b2"};
      }

      @Override
      public Serializable[] setValue(Serializable[] value) {
        return new Serializable[0];
      }
    };

    Mockito.when(eventData.getRows()).thenReturn(Lists.newArrayList(entry));
    processor.process(null, eventData, new Context());
    Mockito.verify(tableMonitorTaskManager, Mockito.times(1))
        .saveOrUpdate(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(RowOP.class));
  }
}