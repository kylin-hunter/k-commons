package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.ex;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.google.common.collect.Lists;
import io.github.kylinhunter.commons.jdbc.binlog.listener.Context;
import io.github.kylinhunter.commons.jdbc.binlog.listener.TableIdManager;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTable;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTables;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.MonitorDeleteRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.dao.constant.RowOP;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableMonitorTaskManager;
import java.io.Serializable;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MonitorDeleteRowsEventDataProcessorTest {

  @Test
  void deleteDataRecord() {

    MonitorTable monitorTable = new MonitorTable();
    monitorTable.setDatabase("");
    monitorTable.setName("");
    monitorTable.setTablePkName("");

    TableIdManager tableIdManager = Mockito.mock(TableIdManager.class);
    ColumnMeta columnMeta = new ColumnMeta();
    Mockito.when(tableIdManager.getPkColumnMeta(anyLong(), anyString(),
        anyString(), anyString())).thenReturn(columnMeta);

    TableMonitorTaskManager tableMonitorTaskManager = Mockito.mock(TableMonitorTaskManager.class);
    MonitorDeleteRowsEventDataProcessor processor = new MonitorDeleteRowsEventDataProcessor(
        tableMonitorTaskManager, new MonitorTables(monitorTable));
    processor.setTableIdManager(tableIdManager);

    DeleteRowsEventData eventData = Mockito.mock(DeleteRowsEventData.class);
    ArrayList<Serializable[]> serializables = Lists.newArrayList(new Serializable[]{"a1", "a2"},
        new Serializable[]{"b1", "b2"});

    Mockito.when(eventData.getRows()).thenReturn(serializables);
    processor.process(null, eventData, new Context());
    Mockito.verify(tableMonitorTaskManager, Mockito.times(2))
        .saveOrUpdate(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(RowOP.class));
  }
}