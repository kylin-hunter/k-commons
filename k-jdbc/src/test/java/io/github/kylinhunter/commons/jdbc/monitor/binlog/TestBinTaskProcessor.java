package io.github.kylinhunter.commons.jdbc.monitor.binlog;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinMonitorConfig;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableTaskManager;
import io.github.kylinhunter.commons.jdbc.monitor.task.AbstractTaskProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.task.TestRowListener;
import javax.sql.DataSource;

public class TestBinTaskProcessor {

  public static void main(String[] args) {
    DataSource dataSource = new DataSourceManager(true).get();
    TestHelper.initTestSQl(dataSource);
    BinMonitorConfig monitorConfig = TestBinLogTableMonitor.getBinMonitorConfig();
    TableTaskManager taskManager = new TableTaskManager(dataSource);
    AbstractTaskProcessor processor = new BinTaskProcessor(taskManager, monitorConfig);
    TestRowListener rowListener = new TestRowListener();
    processor.setRowListener(rowListener);
    processor.reset();
    processor.start();
  }
}