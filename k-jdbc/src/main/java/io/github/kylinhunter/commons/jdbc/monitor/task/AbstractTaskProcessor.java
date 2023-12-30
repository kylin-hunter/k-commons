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

import io.github.kylinhunter.commons.jdbc.monitor.bean.Config;
import io.github.kylinhunter.commons.jdbc.monitor.bean.Table;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableTaskManager;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.constant.RowStatus;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.TableMonitorTask;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-24 01:53
 */
@Slf4j
public abstract class AbstractTaskProcessor implements TaskProcessor {

  private ScheduledExecutorService scheduler;

  protected List<Table> tables;

  protected TableTaskManager taskManager;
  protected Config config;

  @Setter protected RowListener rowListener;

  /**
   * @title reset
   * @description reset
   * @author BiJi'an
   * @date 2023-12-23 21:35
   */
  public void reset() {
    for (Table table : this.tables) {
      taskManager.reset(table);
    }
  }

  /**
   * @param scheduler scheduler
   * @title init
   * @description init
   * @author BiJi'an
   * @date 2023-12-29 22:21
   */
  @Override
  public void setScheduler(ScheduledExecutorService scheduler) {
    this.scheduler = scheduler;
  }

  /**
   * @title start
   * @description start
   * @author BiJi'an
   * @date 2023-12-24 18:04
   */
  @Override
  public void start() {

    this.scheduler.scheduleWithFixedDelay(
        this::execJob, 0, this.config.getExecInterval(), TimeUnit.MILLISECONDS);

    this.scheduler.scheduleWithFixedDelay(
        this::execRetringJob, 0, this.config.getRetryInterval(), TimeUnit.MILLISECONDS);

    this.scheduler.scheduleWithFixedDelay(
        this::execErrorJob, 0, this.config.getErrorInterval(), TimeUnit.MILLISECONDS);
  }

  /**
   * @title execJob
   * @description execJob
   * @author BiJi'an
   * @date 2023-12-25 23:43
   */
  private void execJob() {
    try {
      int maxRetryTimes = config.getMaxRetryTimes();
      for (Table table : tables) {

        String destination = table.getDestination();
        String database = table.getDatabase();
        String tableName = table.getTableName();
        log.info("exec Job {}/{}/{}", destination, database, tableName);
        LocalDateTime endTime =
            LocalDateTime.now().minus(this.config.getExecBefore(), ChronoUnit.MILLIS);

        List<TableMonitorTask> waitDatas =
            this.taskManager.findWaitDatas(destination, endTime, maxRetryTimes, 100);
        if (waitDatas.size() > 0) {
          log.info("wait data size=>{}", waitDatas.size());
          for (TableMonitorTask waitData : waitDatas) {
            log.info("process data {}/{}", waitData.getTableName(), waitData.getDataId());

            this.rowListener.onEvent(this.taskManager, table, destination, waitData);
          }
        }
      }
    } catch (Exception e) {
      log.error("execJob error", e);
    }
  }

  /**
   * @title execRetringJob
   * @description execRetringJob
   * @author BiJi'an
   * @date 2023-12-24 19:17
   */
  private void execRetringJob() {

    try {
      LocalDateTime now = LocalDateTime.now();
      LocalDateTime beforTime = now.minus(this.config.getTryMinTime(), ChronoUnit.MILLIS);

      for (Table scanTable : tables) {
        taskManager.batchRetry(scanTable.getDestination(), RowStatus.RETRYING, beforTime);
      }

      beforTime = now.minus(this.config.getProcessTimeout(), ChronoUnit.MILLIS);
      for (Table scanTable : tables) {
        taskManager.batchRetry(scanTable.getDestination(), RowStatus.PROCESSING, beforTime);
      }
    } catch (Exception e) {
      log.error("exec retring Job error", e);
    }
  }

  /**
   * @title execErrorJob
   * @description execErrorJob
   * @author BiJi'an
   * @date 2023-12-24 19:17
   */
  private void execErrorJob() {

    try {
      for (Table scanTable : tables) {
        taskManager.batchError(
            scanTable.getDestination(), config.getMaxRetryTimes(), LocalDateTime.now());
      }
    } catch (Exception e) {
      log.error("execErrorJob error", e);
    }
  }
}
