package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event;

import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.FormatDescriptionEventData;
import io.github.kylinhunter.commons.jdbc.meta.cache.DatabaseMetaCache;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FormatDescriptionEventDataProcessorTest {

  @Test
  void process() {

    FormatDescriptionEventDataProcessor event = new FormatDescriptionEventDataProcessor();
    EventData eventData = Mockito.mock(FormatDescriptionEventData.class);
    DatabaseMetaCache databaseMetaCache = Mockito.mock(DatabaseMetaCache.class);
    event.setDatabaseMetaCache(databaseMetaCache);
    event.process(null, eventData, new Context());
  }
}