package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event;

import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.RotateEventData;
import io.github.kylinhunter.commons.jdbc.meta.cache.DatabaseMetaCache;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.Context;
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