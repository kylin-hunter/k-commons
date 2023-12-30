package io.github.kylinhunter.commons.jdbc.binlog.listener;

import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import io.github.kylinhunter.commons.jdbc.binlog.listener.event.TableMapEventDataProcessor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TableMapEventDataProcessorTest {

  @Test
  void process() {
    TableMapEventDataProcessor event = new TableMapEventDataProcessor();
    EventData eventData = Mockito.mock(TableMapEventData.class);
    TableIdManager tableIdManager = Mockito.mock(TableIdManager.class);
    event.setTableIdManager(tableIdManager);
    event.process(null, eventData, null);
  }
}