package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener;

import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventHeaderV4;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.listener.event.QueryEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinMonitorConfig;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinTable;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableTaskManager;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TableMonitorListenerTest {

  @Test
  void test() throws SQLException {

    DataSource dataSource = TestHelper.mockDataSource();
    Event event = Mockito.mock(Event.class);
    Mockito.when(event.getHeader()).thenReturn(new EventHeaderV4());

    BinTable binTable = new BinTable();
    BinMonitorConfig binMonitorConfig = new BinMonitorConfig();
    binMonitorConfig.add(binTable);
    TableTaskManager tableTaskManager = Mockito.mock(TableTaskManager.class);

    TableMonitorListener tableMonitorListener = new TableMonitorListener(binMonitorConfig,
        tableTaskManager);
    EventData writeRowsEventData = Mockito.mock(WriteRowsEventData.class);
    Mockito.when(event.getData()).thenReturn(writeRowsEventData);

    tableMonitorListener.addEventProcessor(new QueryEventDataProcessor());

    tableMonitorListener.init(dataSource);
    tableMonitorListener.onEvent(event);
  }
}