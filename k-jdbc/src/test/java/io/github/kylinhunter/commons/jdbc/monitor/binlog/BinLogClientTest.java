package io.github.kylinhunter.commons.jdbc.monitor.binlog;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.TableMonitorListener;

class BinLogClientTest {

  //  @Test
  void init() {

    BinLogConfig binLogConfig = TestBinLogClient.getBinLogConfig();
    binLogConfig.setServerId(2);
    BinLogClient binLogClient = new BinLogClient(binLogConfig);

    TableMonitorConfig tableMonitorConfig = TestBinLogClient.getTableMonitorConfig();
    tableMonitorConfig.setDestination(TestHelper.MONITOR_BINLOG_TASK);
    TableMonitorListener tableMonitorListener = new TableMonitorListener();
    tableMonitorListener.setTableBinlogConfig(tableMonitorConfig);
    binLogClient.addBinLogEventListener(tableMonitorListener);

    Thread thread = new Thread(binLogClient::start);
    thread.start();

    binLogClient.disconnect();


  }
}