package io.github.kylinhunter.commons.jdbc.binlog.listener;

import com.github.shyiko.mysql.binlog.event.QueryEventData;
import io.github.kylinhunter.commons.jdbc.binlog.listener.event.QueryEventDataProcessor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class QueryEventDataProcessorTest {

  @Test
  void process() {

    QueryEventDataProcessor event = new QueryEventDataProcessor();
    QueryEventData eventData = Mockito.mock(QueryEventData.class);
    Mockito.when(eventData.getSql()).thenReturn("alter table xxx");

    TableIdManager tableIdManager = Mockito.mock(TableIdManager.class);
    event.setTableIdManager(tableIdManager);
    event.process(null, eventData, new Context());
  }
}