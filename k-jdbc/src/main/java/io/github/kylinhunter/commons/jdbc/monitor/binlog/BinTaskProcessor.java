package io.github.kylinhunter.commons.jdbc.monitor.binlog;

import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinMonitorConfig;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableTaskManager;
import io.github.kylinhunter.commons.jdbc.monitor.task.AbstractTaskProcessor;
import java.util.ArrayList;
import javax.sql.DataSource;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-23 20:23
 */
public class BinTaskProcessor extends AbstractTaskProcessor {

  private final DataSource dataSource;
  private final BinMonitorConfig binlogConfig;

  public BinTaskProcessor(DataSource dataSource, BinMonitorConfig monitorConfig) {
    this.dataSource = dataSource;
    this.taskManager = new TableTaskManager(dataSource);
    this.binlogConfig = monitorConfig;
    this.config = monitorConfig;
    this.tables = new ArrayList<>(monitorConfig.getBinTables());
  }


}