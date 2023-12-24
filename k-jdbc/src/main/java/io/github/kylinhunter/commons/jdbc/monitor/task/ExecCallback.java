package io.github.kylinhunter.commons.jdbc.monitor.task;

import io.github.kylinhunter.commons.jdbc.monitor.manager.TableTaskManager;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.TableMonitorTask;

public interface ExecCallback {

  void callback(TableTaskManager taskManager, String destination, TableMonitorTask task);

}
