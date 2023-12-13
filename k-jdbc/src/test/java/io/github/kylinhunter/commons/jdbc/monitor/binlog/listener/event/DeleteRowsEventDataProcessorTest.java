package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event;

import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import io.github.kylinhunter.commons.jdbc.meta.cache.DatabaseMetaCache;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeleteRowsEventDataProcessorTest {

  @Test
  void process() {
    DeleteRowsEventDataProcessor event = new DeleteRowsEventDataProcessor();
    EventData eventData = Mockito.mock(DeleteRowsEventData.class);
    DatabaseMetaCache databaseMetaCache = Mockito.mock(DatabaseMetaCache.class);
    event.setDatabaseMetaCache(databaseMetaCache);
    event.process(null, eventData, new Context());
  }
}