package io.github.kylinhunter.commons.jdbc.monitor.binlog;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.jdbc.binlog.BinLogClient;
import io.github.kylinhunter.commons.jdbc.binlog.BinLogConfig;
import io.github.kylinhunter.commons.jdbc.binlog.TestBinLogClient;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BinLogTableMonitorTest {

  @Test
  void start() {

    BinLogConfig binLogConfig = TestBinLogClient.getBinLogConfig();

    BinLogTableMonitor binLogTableMonitor = new BinLogTableMonitor(binLogConfig);

    MonitorTable monitorTable1 = TestBinLogTableMonitor.getMonitorTable1();
    MonitorTable monitorTable2 = TestBinLogTableMonitor.getMonitorTable2();
    binLogTableMonitor.setMonitorTables(ListUtils.newArrayList(monitorTable1, monitorTable2));
    BinLogClient binLogClient = Mockito.mock(BinLogClient.class);
    ReflectUtils.setField(binLogTableMonitor, "binLogClient", binLogClient);

    binLogTableMonitor.reset();
    binLogTableMonitor.start();
  }
}