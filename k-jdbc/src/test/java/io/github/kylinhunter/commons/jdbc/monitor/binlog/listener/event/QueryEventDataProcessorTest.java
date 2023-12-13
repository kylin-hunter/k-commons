package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event;

import com.github.shyiko.mysql.binlog.event.QueryEventData;
import io.github.kylinhunter.commons.jdbc.meta.cache.DatabaseMetaCache;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class QueryEventDataProcessorTest {

  @Test
  void process() {

    QueryEventDataProcessor event = new QueryEventDataProcessor();
    QueryEventData eventData = Mockito.mock(QueryEventData.class);
    Mockito.when(eventData.getSql()).thenReturn("alter table xxx");

    DatabaseMetaCache databaseMetaCache = Mockito.mock(DatabaseMetaCache.class);
    event.setDatabaseMetaCache(databaseMetaCache);
    event.process(null, eventData, new Context());
  }
}