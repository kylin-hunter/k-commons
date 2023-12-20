package io.github.kylinhunter.commons.jdbc.monitor.binlog;

import io.github.kylinhunter.commons.jdbc.binlog.BinLogClient;
import io.github.kylinhunter.commons.jdbc.binlog.BinLogClientTest;
import io.github.kylinhunter.commons.jdbc.binlog.BinLogConfig;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTable;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTables;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BinLogTableMonitorTest {

  @Test
  void start() throws SQLException {

    BinLogConfig binLogConfig = BinLogClientTest.mockBinLogconfig();

    MonitorTable monitorTable1 = TestBinLogTableMonitor.getMonitorTable1();
    MonitorTable monitorTable2 = TestBinLogTableMonitor.getMonitorTable2();
    MonitorTables monitorTables = new MonitorTables();
    monitorTables.addMonitorTable(monitorTable1);
    monitorTables.addMonitorTable(monitorTable2);
    BinLogClient binLogClient = Mockito.mock(BinLogClient.class);

    BinLogTableMonitor binLogTableMonitor = new BinLogTableMonitor(binLogConfig, monitorTables);

    ReflectUtils.setField(binLogTableMonitor, "binLogClient", binLogClient);

    binLogTableMonitor.reset();
    binLogTableMonitor.start();
  }
}