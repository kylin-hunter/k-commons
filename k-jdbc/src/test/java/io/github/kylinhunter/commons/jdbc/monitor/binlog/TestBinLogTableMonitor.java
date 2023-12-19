package io.github.kylinhunter.commons.jdbc.monitor.binlog;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.BinLogConfig;
import io.github.kylinhunter.commons.jdbc.binlog.TestBinLogClient;

class TestBinLogTableMonitor {

  public static void main(String[] args) {
    BinLogConfig binLogConfig = TestBinLogClient.getBinLogConfig();
    BinLogTableMonitor binLogTableMonitor = new BinLogTableMonitor(binLogConfig);

    MonitorTable monitorTable = getMonitorTable1();
    MonitorTable monitorTable2 = getMonitorTable2();
    binLogTableMonitor.setMonitorTables(ListUtils.newArrayList(monitorTable, monitorTable2));
    binLogTableMonitor.reset();
    binLogTableMonitor.start();
  }


  public static MonitorTable getMonitorTable1() {
    MonitorTable tableBinlogConfig = new MonitorTable();
    tableBinlogConfig.setTablePkName("id");
    tableBinlogConfig.setDatabase(TestHelper.DATABASE);
    tableBinlogConfig.setName(TestHelper.TEST_TABLE_ROLE1);
    return tableBinlogConfig;
  }

  public static MonitorTable getMonitorTable2() {
    MonitorTable monitorTable2 = getMonitorTable1();
    monitorTable2.setName(TestHelper.TEST_TABLE_ROLE2);
    return monitorTable2;

  }
}