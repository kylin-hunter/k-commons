package io.github.kylinhunter.commons.jdbc.monitor.binlog;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.BinLogClient;
import io.github.kylinhunter.commons.jdbc.binlog.BinLogConfig;

class BinLogClientTest {

  //  @Test
  void init() {

    BinLogConfig binLogConfig = TestBinLogClient.getBinLogConfig();
    binLogConfig.setServerId(2);
    BinLogClient binLogClient = new BinLogClient(binLogConfig);

    MonitorTable monitorTable = TestBinLogClient.getTableMonitorConfig();
    monitorTable.setDestination(TestHelper.MONITOR_BINLOG_TASK);
    TableMonitorListener tableMonitorListener = new TableMonitorListener();
    tableMonitorListener.setMonitorTable(monitorTable);
    binLogClient.addBinLogEventListener(tableMonitorListener);

    Thread thread = new Thread(binLogClient::start);
    thread.start();

    binLogClient.disconnect();


  }
}