package io.github.kylinhunter.commons.jdbc.binlog.listener;

import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.FormatDescriptionEventData;
import io.github.kylinhunter.commons.jdbc.binlog.listener.event.FormatDescriptionEventDataProcessor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FormatDescriptionEventDataProcessorTest {

  @Test
  void process() {

    FormatDescriptionEventDataProcessor event = new FormatDescriptionEventDataProcessor();
    EventData eventData = Mockito.mock(FormatDescriptionEventData.class);
    TableIdManager tableIdManager = Mockito.mock(TableIdManager.class);
    event.setTableIdManager(tableIdManager);
    event.process(null, eventData, new Context());
  }
}