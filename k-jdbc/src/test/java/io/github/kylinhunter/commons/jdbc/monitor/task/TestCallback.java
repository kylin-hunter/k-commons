package io.github.kylinhunter.commons.jdbc.monitor.task;

import io.github.kylinhunter.commons.jdbc.monitor.manager.TableTaskManager;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.TableMonitorTask;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-24 18:12
 */
@Slf4j
public class TestCallback implements ExecCallback {

  @Override
  public void callback(TableTaskManager taskManager, String destination,
      TableMonitorTask task) {
    if ("1".equals(task.getDataId())) {
      taskManager.setSuccess(destination, task);
      log.info("{}/{} set setSuccess", task.getTableName(), task.getDataId());


    } else if ("2".equals(task.getDataId())) {
      taskManager.setError(destination, task);
      log.info("{}/{} set setError", task.getTableName(), task.getDataId());


    } else {
      taskManager.setRetry(destination, task);
      log.info("{}/{} set setRetry", task.getTableName(), task.getDataId());
    }


  }
}