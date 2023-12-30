package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.processor;

import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.google.common.collect.Lists;
import io.github.kylinhunter.commons.jdbc.binlog.listener.Context;
import io.github.kylinhunter.commons.jdbc.binlog.listener.TableIdManager;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinMonitorConfig;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinTable;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableTaskManager;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.constant.RowOP;
import java.io.Serializable;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ExUpdateRowsEventDataProcessorTest {

  @Test
  void updateDataRecord() {

    BinTable binTable = new BinTable();
    binTable.setDatabase("database");
    binTable.setTableName("tableName");
    binTable.setPkColName("id");
    TableIdManager tableIdManager = ExDeleteRowsEventDataProcessorTest.mockeTableIdManager();

    BinMonitorConfig binMonitorConfig = new BinMonitorConfig();
    binMonitorConfig.add(binTable);
    TableTaskManager tableTaskManager = Mockito.mock(TableTaskManager.class);
    ExUpdateRowsEventDataProcessor processor = new ExUpdateRowsEventDataProcessor(
        tableTaskManager,
        new TableProcessor(tableIdManager, binMonitorConfig));
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
    Mockito.verify(tableTaskManager, Mockito.times(2))
        .save(Mockito.any(BinTable.class), Mockito.anyString(),
            Mockito.any(RowOP.class));
  }
}