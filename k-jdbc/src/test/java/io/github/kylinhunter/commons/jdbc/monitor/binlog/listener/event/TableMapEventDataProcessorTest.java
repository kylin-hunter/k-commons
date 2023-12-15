package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event;

import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import io.github.kylinhunter.commons.jdbc.binlog.listener.event.TableMapEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.meta.cache.DatabaseMetaCache;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TableMapEventDataProcessorTest {

  @Test
  void process() {
    TableMapEventDataProcessor event = new TableMapEventDataProcessor();
    EventData eventData = Mockito.mock(TableMapEventData.class);
    DatabaseMetaCache databaseMetaCache = Mockito.mock(DatabaseMetaCache.class);
    event.setDatabaseMetaCache(databaseMetaCache);
    event.process(null, eventData, null);
  }
}