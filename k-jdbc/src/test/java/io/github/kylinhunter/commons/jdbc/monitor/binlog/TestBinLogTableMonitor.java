package io.github.kylinhunter.commons.jdbc.monitor.binlog;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.TestBinLogClient;
import io.github.kylinhunter.commons.jdbc.binlog.bean.BinLogConfig;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import io.github.kylinhunter.commons.jdbc.monitor.TableMonitor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTable;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTables;
import javax.sql.DataSource;

class TestBinLogTableMonitor {

  public static void main(String[] args) {
    DataSource dataSource = new DataSourceManager(true).get();
    TestHelper.initTestSQl(dataSource);

    BinLogConfig binLogConfig = TestBinLogClient.getBinLogConfig();
    binLogConfig.setSavePointManager(TestBinLogClient.getRedisSavePointManager1());
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
    MonitorTable monitorTable = new MonitorTable();
    monitorTable.setTablePkName("id");
    monitorTable.setDatabase(TestHelper.DATABASE);
    monitorTable.setName(TestHelper.TEST_TABLE_ROLE1);
    monitorTable.setDestination(TestHelper.MONITOR_TASK_BINLOG);
    return monitorTable;
  }

  public static MonitorTable getMonitorTable2() {
    MonitorTable monitorTable2 = getMonitorTable1();
    monitorTable2.setName(TestHelper.TEST_TABLE_ROLE2);
    return monitorTable2;

  }
}