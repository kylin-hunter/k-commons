package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.ex;

import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import io.github.kylinhunter.commons.jdbc.meta.cache.DatabaseMetaCache;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.Context;
import org.mockito.Mockito;

class MonitorDeleteRowsEventDataProcessorTest {

  //  @Test
  void deleteDataRecord() {
    MonitorDeleteRowsEventDataProcessor processor = new MonitorDeleteRowsEventDataProcessor();

    EventData eventData = Mockito.mock(DeleteRowsEventData.class);
    DatabaseMetaCache databaseMetaCache = Mockito.mock(DatabaseMetaCache.class);
    processor.setDatabaseMetaCache(databaseMetaCache);
    processor.process(null, eventData, new Context());
  }
}