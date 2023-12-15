package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener;

import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventHeaderV4;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import io.github.kylinhunter.commons.jdbc.binlog.listener.event.QueryEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.MonitorTable;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.TableMonitorContext;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.TableMonitorListener;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TableMonitorListenerTest {

  @Test
  void test() {

    DataSourceManager dataSourceManager = new DataSourceManager(true);
    Event event = Mockito.mock(Event.class);
    Mockito.when(event.getHeader()).thenReturn(new EventHeaderV4());
    TableMonitorListener tableMonitorListener = new TableMonitorListener();
    EventData writeRowsEventData = Mockito.mock(WriteRowsEventData.class);
    Mockito.when(event.getData()).thenReturn(writeRowsEventData);

    TableMonitorContext context = new TableMonitorContext();
    tableMonitorListener.setContext(context);
    tableMonitorListener.addEventProcessor(new QueryEventDataProcessor());
    MonitorTable monitorTable = new MonitorTable();
    tableMonitorListener.setMonitorTable(monitorTable);
    tableMonitorListener.init(dataSourceManager.get());
    tableMonitorListener.onEvent(event);
    dataSourceManager.close();
  }
}