package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event;

import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import io.github.kylinhunter.commons.jdbc.meta.cache.DatabaseMetaCache;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class WriteRowsEventDataProcessorTest {

  @Test
  void test() {
    WriteRowsEventDataProcessor event = new WriteRowsEventDataProcessor();
    EventData eventData = Mockito.mock(WriteRowsEventData.class);
    DatabaseMetaCache databaseMetaCache = Mockito.mock(DatabaseMetaCache.class);
    event.setDatabaseMetaCache(databaseMetaCache);
    event.process(null, eventData, new Context());
  }

}