package io.github.kylinhunter.commons.jdbc.monitor.scan;

import io.github.kylinhunter.commons.jdbc.monitor.manager.TableTaskManager;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.TableScanConfig;
import io.github.kylinhunter.commons.jdbc.monitor.task.AbstractTaskProcessor;
import java.util.ArrayList;
import javax.sql.DataSource;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-23 20:23
 */
public class ScanTaskProcessor extends AbstractTaskProcessor {

  private final DataSource dataSource;

  private final TableScanConfig tableScanConfig;


  public ScanTaskProcessor(DataSource dataSource, TableScanConfig tableScanConfig) {
    this.dataSource = dataSource;
    this.taskManager = new TableTaskManager(dataSource);
    this.tableScanConfig = tableScanConfig;
    this.config = tableScanConfig;
    this.tables = new ArrayList<>(tableScanConfig.getScanTables());
  }


}