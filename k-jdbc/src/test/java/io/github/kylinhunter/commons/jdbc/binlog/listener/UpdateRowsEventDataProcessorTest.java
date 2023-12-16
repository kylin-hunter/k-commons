package io.github.kylinhunter.commons.jdbc.binlog.listener;

import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import io.github.kylinhunter.commons.jdbc.binlog.listener.event.UpdateRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.meta.cache.DatabaseMetaCache;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UpdateRowsEventDataProcessorTest {

  @Test
  void process() {
    UpdateRowsEventDataProcessor event = new UpdateRowsEventDataProcessor();
    EventData eventData = Mockito.mock(UpdateRowsEventData.class);
    DatabaseMetaCache databaseMetaCache = Mockito.mock(DatabaseMetaCache.class);
    event.setDatabaseMetaCache(databaseMetaCache);
    event.process(null, eventData, new Context());
  }
}