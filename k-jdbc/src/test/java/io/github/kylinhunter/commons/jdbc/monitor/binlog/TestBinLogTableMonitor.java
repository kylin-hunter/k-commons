package io.github.kylinhunter.commons.jdbc.monitor.binlog;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.BinLogConfig;
import io.github.kylinhunter.commons.jdbc.binlog.TestBinLogClient;
import io.github.kylinhunter.commons.jdbc.monitor.TableMonitor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTable;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTables;

class TestBinLogTableMonitor {

  public static void main(String[] args) {
    BinLogConfig binLogConfig = TestBinLogClient.getBinLogConfig();

    MonitorTable monitorTable1 = getMonitorTable1();
    MonitorTable monitorTable2 = getMonitorTable2();
    MonitorTables monitorTables = new MonitorTables();
    monitorTables.addMonitorTable(monitorTable1);
    monitorTables.addMonitorTable(monitorTable2);

    TableMonitor tableMonitor = new BinLogTableMonitor(binLogConfig, monitorTables);
    tableMonitor.reset();
    tableMonitor.start();
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