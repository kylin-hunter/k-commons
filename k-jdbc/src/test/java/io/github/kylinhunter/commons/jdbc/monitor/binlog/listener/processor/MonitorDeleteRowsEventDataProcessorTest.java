package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.processor;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.google.common.collect.Lists;
import io.github.kylinhunter.commons.jdbc.binlog.listener.Context;
import io.github.kylinhunter.commons.jdbc.binlog.listener.TableIdManager;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetas;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTable;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTables;
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
    monitorTable.setDatabase("database");
    monitorTable.setName("tableName");
    monitorTable.setTablePkName("id");
    TableIdManager tableIdManager = mockeTableIdManager();

    TableMonitorTaskManager tableMonitorTaskManager = Mockito.mock(TableMonitorTaskManager.class);
    MonitorDeleteRowsEventDataProcessor processor = new MonitorDeleteRowsEventDataProcessor(
        tableMonitorTaskManager, new MonitorTables(monitorTable),
        new MonitorManager(tableIdManager));
    processor.setTableIdManager(tableIdManager);

    DeleteRowsEventData eventData = Mockito.mock(DeleteRowsEventData.class);
    ArrayList<Serializable[]> serializables = Lists.newArrayList(new Serializable[]{"a1", "a2"},
        new Serializable[]{"b1", "b2"});

    when(eventData.getRows()).thenReturn(serializables);
    processor.process(null, eventData, new Context());
    Mockito.verify(tableMonitorTaskManager, Mockito.times(2))
        .saveOrUpdate(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(RowOP.class));
  }

  public static TableIdManager mockeTableIdManager() {

    TableIdManager tableIdManager = Mockito.mock(TableIdManager.class);
    TableMeta tableMeta = new TableMeta();
    tableMeta.setDatabase("database");
    tableMeta.setName("tableName");
    when(tableIdManager.getTableMeta(anyLong())).thenReturn(tableMeta);

    ColumnMetas columnMetas = new ColumnMetas();
    ColumnMeta columnMeta = new ColumnMeta();
    columnMeta.setColumnName("id");
    columnMetas.addColumnMeta(columnMeta);
    when(tableIdManager.getColumnMetas(anyLong())).thenReturn(columnMetas);
    return tableIdManager;
  }
}