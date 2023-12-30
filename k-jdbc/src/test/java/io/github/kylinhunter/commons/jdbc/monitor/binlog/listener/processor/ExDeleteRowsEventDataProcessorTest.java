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
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinMonitorConfig;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinTable;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableTaskManager;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.constant.RowOP;
import java.io.Serializable;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ExDeleteRowsEventDataProcessorTest {

  @Test
  void deleteDataRecord() {
    BinTable binTable = new BinTable();
    binTable.setDatabase("database");
    binTable.setTableName("tableName");
    binTable.setPkColName("id");
    TableIdManager tableIdManager = mockeTableIdManager();
    BinMonitorConfig binMonitorConfig = new BinMonitorConfig();
    binMonitorConfig.add(binTable);
    TableTaskManager tableTaskManager = Mockito.mock(TableTaskManager.class);
    ExDeleteRowsEventDataProcessor processor = new ExDeleteRowsEventDataProcessor(
        tableTaskManager,
        new TableProcessor(tableIdManager, binMonitorConfig));
    processor.setTableIdManager(tableIdManager);

    DeleteRowsEventData eventData = Mockito.mock(DeleteRowsEventData.class);
    ArrayList<Serializable[]> serializables = Lists.newArrayList(new Serializable[]{"a1", "a2"},
        new Serializable[]{"b1", "b2"});

    when(eventData.getRows()).thenReturn(serializables);
    processor.process(null, eventData, new Context());
    Mockito.verify(tableTaskManager, Mockito.times(2))
        .save(Mockito.any(BinTable.class), Mockito.anyString(),
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