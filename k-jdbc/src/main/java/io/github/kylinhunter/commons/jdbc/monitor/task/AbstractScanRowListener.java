/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.jdbc.monitor.task;

import io.github.kylinhunter.commons.jdbc.exception.JdbcException;
import io.github.kylinhunter.commons.jdbc.monitor.bean.Table;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableTaskManager;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.TableMonitorTask;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-24 01:53
 */
@Slf4j
public abstract class AbstractScanRowListener extends AbstractRowListener {

  /**
   * @param taskManager taskManager
   * @param destination destination
   * @param task task
   * @title insert
   * @description insert
   * @author BiJi'an
   * @date 2023-12-30 17:00
   */
  protected void insert(
      TableTaskManager taskManager, Table table, String destination, TableMonitorTask task) {
    try {
      if (taskManager.isDeleted(table, task.getDataId())) {
        delete(table, task.getDataId());
      } else {
        insert(table, task.getDataId());
      }
      taskManager.setSuccess(destination, task);
    } catch (JdbcException e) {
      throw e;
    } catch (Exception e) {
      throw new JdbcException("insert error", e);
    }
  }

  /**
   * @param taskManager taskManager
   * @param destination destination
   * @param task task
   * @title update
   * @description update
   * @author BiJi'an
   * @date 2023-12-30 17:00
   */
  protected void update(
      TableTaskManager taskManager, Table table, String destination, TableMonitorTask task) {
    try {
      if (taskManager.isDeleted(table, task.getDataId())) {
        delete(table, task.getDataId());
      } else {
        update(table, task.getDataId());
      }
      taskManager.setSuccess(destination, task);
    } catch (JdbcException e) {
      throw e;
    } catch (Exception e) {
      throw new JdbcException("update error", e);
    }
  }
}
