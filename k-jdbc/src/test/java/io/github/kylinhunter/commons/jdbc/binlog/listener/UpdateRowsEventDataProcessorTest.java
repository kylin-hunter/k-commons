package io.github.kylinhunter.commons.jdbc.binlog.listener;

import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import io.github.kylinhunter.commons.jdbc.binlog.listener.event.UpdateRowsEventDataProcessor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UpdateRowsEventDataProcessorTest {

  @Test
  void process() {
    UpdateRowsEventDataProcessor event = new UpdateRowsEventDataProcessor();
    EventData eventData = Mockito.mock(UpdateRowsEventData.class);
    TableIdManager tableIdManager = Mockito.mock(TableIdManager.class);
    event.setTableIdManager(tableIdManager);
    event.process(null, eventData, new Context());
  }
}