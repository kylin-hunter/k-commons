package io.github.kylinhunter.commons.jdbc.monitor.binlog;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.TestBinLogClient;
import io.github.kylinhunter.commons.jdbc.binlog.bean.BinConfig;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import io.github.kylinhunter.commons.jdbc.monitor.TableMonitor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinMonitorConfig;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinTable;
import javax.sql.DataSource;

class TestBinLogTableMonitor {

  public static void main(String[] args) {
    DataSource dataSource = new DataSourceManager(true).get();
    TestHelper.initTestSQl(dataSource);

    BinConfig binConfig = TestBinLogClient.getBinLogConfig();
    binConfig.setSavePointManager(TestBinLogClient.getRedisSavePointManager1());

    BinMonitorConfig monitorConfig = getBinMonitorConfig();
    TableMonitor tableMonitor = new BinTableMonitor(binConfig, monitorConfig);
    tableMonitor.reset();
    tableMonitor.start();
  }


  public static BinMonitorConfig getBinMonitorConfig() {
    BinTable binTable1 = getMonitorTable1();
    BinTable binTable2 = getMonitorTable2();
    BinMonitorConfig monitorConfig = new BinMonitorConfig();
    monitorConfig.add(binTable1);
    monitorConfig.add(binTable2);
    return monitorConfig;
  }

  public static BinTable getMonitorTable1() {
    BinTable binTable = new BinTable();
    binTable.setPkColName("id");
    binTable.setDatabase(TestHelper.DATABASE);
    binTable.setTableName(TestHelper.TEST_TABLE_ROLE1);
    binTable.setDestination(TestHelper.TEST_BINLOG_TASK);
    return binTable;
  }

  public static BinTable getMonitorTable2() {
    BinTable binTable2 = getMonitorTable1();
    binTable2.setTableName(TestHelper.TEST_TABLE_ROLE2);
    return binTable2;

  }
}