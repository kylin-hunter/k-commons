package io.github.kylinhunter.commons.jdbc.binlog.listener;

import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.RotateEventData;
import io.github.kylinhunter.commons.jdbc.binlog.listener.event.RotateEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.meta.cache.DatabaseMetaCache;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RotateEventDataProcessorTest {

  @Test
  void process() {
    RotateEventDataProcessor event = new RotateEventDataProcessor();
    EventData eventData = Mockito.mock(RotateEventData.class);
    DatabaseMetaCache databaseMetaCache = Mockito.mock(DatabaseMetaCache.class);
    event.setDatabaseMetaCache(databaseMetaCache);
    event.process(null, eventData, new Context());
  }
}