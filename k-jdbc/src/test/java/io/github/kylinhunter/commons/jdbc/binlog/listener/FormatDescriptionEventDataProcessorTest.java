package io.github.kylinhunter.commons.jdbc.binlog.listener;

import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.FormatDescriptionEventData;
import io.github.kylinhunter.commons.jdbc.binlog.listener.event.FormatDescriptionEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.meta.cache.DatabaseMetaCache;
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