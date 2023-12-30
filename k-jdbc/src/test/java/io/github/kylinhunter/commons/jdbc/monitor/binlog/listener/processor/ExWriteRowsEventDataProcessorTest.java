package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.processor;

import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import com.google.common.collect.Lists;
import io.github.kylinhunter.commons.jdbc.binlog.listener.Context;
import io.github.kylinhunter.commons.jdbc.binlog.listener.TableIdManager;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinMonitorConfig;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinTable;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableTaskManager;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.constant.RowOP;
import java.io.Serializable;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ExWriteRowsEventDataProcessorTest {

  @Test
  void insertDataRecord() {
    BinTable binTable = new BinTable();
    binTable.setDatabase("database");
    binTable.setTableName("tableName");
    binTable.setPkColName("id");
    TableIdManager tableIdManager = ExDeleteRowsEventDataProcessorTest.mockeTableIdManager();

    BinMonitorConfig binMonitorConfig = new BinMonitorConfig();
    binMonitorConfig.add(binTable);
    TableTaskManager tableTaskManager = Mockito.mock(TableTaskManager.class);
    ExWriteRowsEventDataProcessor processor = new ExWriteRowsEventDataProcessor(
        tableTaskManager,
        new TableProcessor(tableIdManager, binMonitorConfig));
    processor.setTableIdManager(tableIdManager);

    WriteRowsEventData eventData = Mockito.mock(WriteRowsEventData.class);
    ArrayList<Serializable[]> serializables = Lists.newArrayList(new Serializable[]{"a1", "a2"},
        new Serializable[]{"b1", "b2"});

    Mockito.when(eventData.getRows()).thenReturn(serializables);
    processor.process(null, eventData, new Context());
    Mockito.verify(tableTaskManager, Mockito.times(2))
        .save(Mockito.any(BinTable.class), Mockito.anyString(),
            Mockito.any(RowOP.class));
  }
}