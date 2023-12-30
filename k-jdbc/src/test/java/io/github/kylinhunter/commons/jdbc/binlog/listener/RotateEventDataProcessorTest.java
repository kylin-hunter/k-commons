package io.github.kylinhunter.commons.jdbc.binlog.listener;

import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.RotateEventData;
import io.github.kylinhunter.commons.jdbc.binlog.listener.event.RotateEventDataProcessor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RotateEventDataProcessorTest {

  @Test
  void process() {
    RotateEventDataProcessor event = new RotateEventDataProcessor();
    EventData eventData = Mockito.mock(RotateEventData.class);
    TableIdManager tableIdManager = Mockito.mock(TableIdManager.class);
    event.setTableIdManager(tableIdManager);
    event.process(null, eventData, new Context());
  }
}