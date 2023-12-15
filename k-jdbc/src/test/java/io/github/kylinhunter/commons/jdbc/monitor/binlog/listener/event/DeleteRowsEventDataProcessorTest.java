package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event;

import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import io.github.kylinhunter.commons.jdbc.binlog.listener.Context;
import io.github.kylinhunter.commons.jdbc.binlog.listener.event.DeleteRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.meta.cache.DatabaseMetaCache;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeleteRowsEventDataProcessorTest {

  @Test
  void process() {
    DeleteRowsEventDataProcessor processor = new DeleteRowsEventDataProcessor();
    EventData eventData = Mockito.mock(DeleteRowsEventData.class);
    DatabaseMetaCache databaseMetaCache = Mockito.mock(DatabaseMetaCache.class);
    processor.setDatabaseMetaCache(databaseMetaCache);
    processor.process(null, eventData, new Context());
  }
}