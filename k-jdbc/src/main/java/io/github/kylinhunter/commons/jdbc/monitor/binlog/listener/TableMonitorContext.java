package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener;

import io.github.kylinhunter.commons.jdbc.monitor.binlog.TableMonitorConfig;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableMonitorTaskManager;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-14 23:46
 */
@Getter
@Setter
public class TableMonitorContext extends Context {

  private TableMonitorTaskManager tableMonitorTaskManager;

  private TableMonitorConfig tableMonitorConfig;

}