package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener;

import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventHeaderV4;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import io.github.kylinhunter.commons.jdbc.binlog.listener.event.QueryEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTable;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTables;
import org.mockito.Mockito;

public class TestTableMonitorListener {

  public static void main(String[] args) {
    DataSourceManager dataSourceManager = new DataSourceManager(true);
    Event event = Mockito.mock(Event.class);
    Mockito.when(event.getHeader()).thenReturn(new EventHeaderV4());
    TableMonitorListener tableMonitorListener = new TableMonitorListener();
    EventData writeRowsEventData = Mockito.mock(WriteRowsEventData.class);
    Mockito.when(event.getData()).thenReturn(writeRowsEventData);

    tableMonitorListener.addEventProcessor(new QueryEventDataProcessor());
    MonitorTable monitorTable = new MonitorTable();
    MonitorTables monitorTables = new MonitorTables(monitorTable);
    tableMonitorListener.setMonitorTables(monitorTables);
    tableMonitorListener.init(dataSourceManager.get());
    tableMonitorListener.onEvent(event);
    dataSourceManager.close();
  }
}