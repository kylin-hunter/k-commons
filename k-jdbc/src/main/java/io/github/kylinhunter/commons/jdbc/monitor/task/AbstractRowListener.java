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

import io.github.kylinhunter.commons.jdbc.exception.FastFailException;
import io.github.kylinhunter.commons.jdbc.exception.JdbcException;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableTaskManager;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.constant.RowOP;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.TableMonitorTask;
import io.github.kylinhunter.commons.lang.EnumUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-24 01:53
 */
@Slf4j
public abstract class AbstractRowListener implements RowListener {

  @Override
  public void onEvent(TableTaskManager taskManager, String destination, TableMonitorTask task) {
    if (task != null) {
      log.info("destination:{},task:{}", destination, task);
      int op = task.getOp();
      RowOP rowOP = EnumUtils.fromCode(RowOP.class, op, false);
      if (rowOP != null) {
        try {
          switch (rowOP) {
            case INSERT:
              insert(taskManager, destination, task);
              break;
            case UPDATE:
              update(taskManager, destination, task);
              break;
            case DELETE:
              delete(taskManager, destination, task);
              break;
            default:
              log.error("op:{} not support", op);
          }
        } catch (Exception e) {
          log.error("callback error", e);
          if (e instanceof FastFailException) {
            taskManager.setError(destination, task);
          } else {
            taskManager.setRetry(destination, task);
          }
        }
      } else {
        log.error("op:{} not support", op);
      }
    }
  }

  /**
   * @param taskManager taskManager
   * @param destination destination
   * @param task        task
   * @title insert
   * @description insert
   * @author BiJi'an
   * @date 2023-12-30 17:00
   */
  protected void insert(TableTaskManager taskManager, String destination, TableMonitorTask task) {
    try {
      insert(task.getDb(), task.getTableName(), task.getDataId());
      taskManager.setSuccess(destination, task);
    } catch (JdbcException e) {
      throw e;
    } catch (Exception e) {
      throw new JdbcException("insert error", e);
    }
  }

  /**
   * the insert event
   *
   * @param database  database
   * @param tableName tableName
   * @param dataId    the data id (primary key)
   */
  public abstract void insert(String database, String tableName, String dataId);

  /**
   * @param taskManager taskManager
   * @param destination destination
   * @param task        task
   * @title update
   * @description update
   * @author BiJi'an
   * @date 2023-12-30 17:00
   */
  protected void update(TableTaskManager taskManager, String destination, TableMonitorTask task) {
    try {
      insert(task.getDb(), task.getTableName(), task.getDataId());
      taskManager.setSuccess(destination, task);
    } catch (JdbcException e) {
      throw e;
    } catch (Exception e) {
      throw new JdbcException("insert error", e);
    }
  }

  /**
   * the update event
   *
   * @param database  database
   * @param tableName tableName
   * @param dataId    the data id (primary key)
   */
  public abstract void update(String database, String tableName, String dataId);


  /**
   * @param taskManager taskManager
   * @param destination destination
   * @param task        task
   * @title delete
   * @description delete
   * @author BiJi'an
   * @date 2023-12-30 17:00
   */
  protected void delete(TableTaskManager taskManager, String destination, TableMonitorTask task) {
    try {
      insert(task.getDb(), task.getTableName(), task.getDataId());
      taskManager.setSuccess(destination, task);
    } catch (JdbcException e) {
      throw e;
    } catch (Exception e) {
      throw new JdbcException("insert error", e);
    }
  }

  /**
   * the delete event
   *
   * @param database  database
   * @param tableName tableName
   * @param dataId    the data id (primary key)
   */
  public abstract void delete(String database, String tableName, String dataId);


}

