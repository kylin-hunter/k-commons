package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.processor;

import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.google.common.collect.Lists;
import io.github.kylinhunter.commons.jdbc.binlog.listener.Context;
import io.github.kylinhunter.commons.jdbc.binlog.listener.TableIdManager;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTable;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTables;
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
    monitorTable.setDatabase("database");
    monitorTable.setName("tableName");
    monitorTable.setTablePkName("id");
    TableIdManager tableIdManager = MonitorDeleteRowsEventDataProcessorTest.mockeTableIdManager();

    TableMonitorTaskManager tableMonitorTaskManager = Mockito.mock(TableMonitorTaskManager.class);
    MonitorUpdateRowsEventDataProcessor processor = new MonitorUpdateRowsEventDataProcessor(
        tableMonitorTaskManager, new MonitorTables(monitorTable),
        new MonitorManager(tableIdManager));
    processor.setTableIdManager(tableIdManager);

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
    Mockito.verify(tableMonitorTaskManager, Mockito.times(2))
        .saveOrUpdate(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
            Mockito.anyString(),
            Mockito.any(RowOP.class));
  }
}