package io.github.kylinhunter.commons.jdbc.binlog.listener;

import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import io.github.kylinhunter.commons.jdbc.binlog.listener.event.WriteRowsEventDataProcessor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class WriteRowsEventDataProcessorTest {

  @Test
  void test() {
    WriteRowsEventDataProcessor event = new WriteRowsEventDataProcessor();
    EventData eventData = Mockito.mock(WriteRowsEventData.class);
    TableIdManager tableIdManager = Mockito.mock(TableIdManager.class);
    event.setTableIdManager(tableIdManager);
    event.process(null, eventData, new Context());
  }

}