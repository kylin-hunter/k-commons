package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.ex;

import static org.mockito.ArgumentMatchers.anyString;

import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import com.google.common.collect.Lists;
import io.github.kylinhunter.commons.jdbc.binlog.listener.Context;
import io.github.kylinhunter.commons.jdbc.binlog.listener.TableIdManager;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTable;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTables;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.MonitorManager;
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

    TableIdManager tableIdManager = Mockito.mock(TableIdManager.class);

    TableMonitorTaskManager tableMonitorTaskManager = Mockito.mock(TableMonitorTaskManager.class);
    MonitorWriteRowsEventDataProcessor processor = new MonitorWriteRowsEventDataProcessor(
        tableMonitorTaskManager, new MonitorTables(monitorTable),
        new MonitorManager(tableIdManager));
    processor.setTableIdManager(tableIdManager);

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