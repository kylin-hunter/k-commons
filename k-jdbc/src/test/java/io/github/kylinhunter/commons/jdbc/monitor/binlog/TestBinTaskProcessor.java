package io.github.kylinhunter.commons.jdbc.monitor.binlog;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinMonitorConfig;
import io.github.kylinhunter.commons.jdbc.monitor.task.AbstractTaskProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.task.TestCallback;
import javax.sql.DataSource;

public class TestBinTaskProcessor {

  public static void main(String[] args) {
    DataSource dataSource = new DataSourceManager(true).get();
    TestHelper.initTestSQl(dataSource);
    BinMonitorConfig monitorConfig = TestBinLogTableMonitor.getBinMonitorConfig();

    AbstractTaskProcessor processor = new BinTaskProcessor(dataSource, monitorConfig);
    TestCallback testCallback = new TestCallback();
    processor.setExecCallback(testCallback);
    processor.reset();
    processor.start();
  }
}