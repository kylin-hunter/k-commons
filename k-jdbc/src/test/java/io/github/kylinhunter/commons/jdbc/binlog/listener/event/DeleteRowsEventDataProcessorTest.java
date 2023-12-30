package io.github.kylinhunter.commons.jdbc.binlog.listener.event;

import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import io.github.kylinhunter.commons.jdbc.binlog.listener.Context;
import io.github.kylinhunter.commons.jdbc.binlog.listener.TableIdManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeleteRowsEventDataProcessorTest {

  @Test
  void process() {
    DeleteRowsEventDataProcessor processor = new DeleteRowsEventDataProcessor();
    EventData eventData = Mockito.mock(DeleteRowsEventData.class);
    TableIdManager tableIdManager = Mockito.mock(TableIdManager.class);
    processor.setTableIdManager(tableIdManager);
    processor.process(null, eventData, new Context());
  }
}