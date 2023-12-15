package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.ex;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import com.google.common.collect.Lists;
import io.github.kylinhunter.commons.jdbc.binlog.listener.Context;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.cache.DatabaseMetaCache;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.MonitorTable;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.MonitorWriteRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.dao.constant.RowOP;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableMonitorTaskManager;
import java.io.Serializable;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MonitorWriteRowsEventDataProcessorTest {

  @Test
  void insertDataRecord() {
    MonitorTable monitorTable = new MonitorTable();
    monitorTable.setDatabase("");
    monitorTable.setName("");
    monitorTable.setTablePkName("");

    DatabaseMetaCache databaseMetaCache = Mockito.mock(DatabaseMetaCache.class);
    ColumnMeta columnMeta = new ColumnMeta();
    Mockito.when(databaseMetaCache.getPkColumnMeta(anyLong(), anyString(),
        anyString(), anyString())).thenReturn(columnMeta);

    TableMonitorTaskManager tableMonitorTaskManager = Mockito.mock(TableMonitorTaskManager.class);
    MonitorWriteRowsEventDataProcessor processor = new MonitorWriteRowsEventDataProcessor(
        tableMonitorTaskManager,
        monitorTable);
    processor.setDatabaseMetaCache(databaseMetaCache);

    WriteRowsEventData eventData = Mockito.mock(WriteRowsEventData.class);
    ArrayList<Serializable[]> serializables = Lists.newArrayList(new Serializable[]{"a1", "a2"},
        new Serializable[]{"b1", "b2"});

    Mockito.when(eventData.getRows()).thenReturn(serializables);
    processor.process(null, eventData, new Context());
    Mockito.verify(tableMonitorTaskManager, Mockito.times(2))
        .saveOrUpdate(anyString(), anyString(), anyString(),
            Mockito.any(RowOP.class));
  }
}