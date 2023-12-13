package io.github.kylinhunter.commons.jdbc.monitor.binlog;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableId;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.TableMonitorListener;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.savepoint.SavePointManager;

class BinLogClientTest {

  //  @Test
  void init() {
    BinLogClient binLogClient = new BinLogClient(TestHelper.MYSQL_JDBC_URL, "root", "root");

    new Thread(() -> {
      SavePointManager redisSavePointManager = TestBinLogClient.getRedisSavePointManager1();
      binLogClient.setSavePointManager(redisSavePointManager);
      binLogClient.setBinlogFilename("binlog.000029");
      binLogClient.setBinlogPosition(1776364);
      binLogClient.setServerId(2);
      TableMonitorListener tableMonitorListener = new TableMonitorListener();
      tableMonitorListener.setTargetTableId(
          new TableId(TestHelper.DATABASE, TestHelper.TEST_TABLE_ROLE));
      tableMonitorListener.setDestination(TestHelper.MONITOR_BINLOG_TASK);
      tableMonitorListener.setTargetTablePK("id");
      binLogClient.addBinLogEventListener(tableMonitorListener);

      binLogClient.start();
    }).start();

    binLogClient.disconnect();


  }
}