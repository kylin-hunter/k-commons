package io.github.kylinhunter.commons.jdbc.monitor.task;

import io.github.kylinhunter.commons.jdbc.monitor.bean.Config;
import io.github.kylinhunter.commons.jdbc.monitor.bean.Table;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableTaskManager;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.constant.RowStatus;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.TableMonitorTask;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
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
public class AbstractTaskProcessor implements TaskProcessor {

  private ScheduledExecutorService scheduler;
  protected List<Table> tables;

  protected TableTaskManager taskManager;
  protected Config config;

  @Setter
  protected ExecCallback execCallback;


  /**
   * @return void
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
   * @title start
   * @description start
   * @author BiJi'an
   * @date 2023-12-24 18:04
   */
  @Override
  public void start() {
    this.scheduler = Executors.newScheduledThreadPool(this.config.getThreadPoolSize());

    this.scheduler.scheduleWithFixedDelay(() -> execJob(), 0, this.config.getExecInterval(),
        TimeUnit.MILLISECONDS);

    this.scheduler.scheduleWithFixedDelay(() -> execRetringJob(), 0, this.config.getRetryInterval(),
        TimeUnit.MILLISECONDS);

    this.scheduler.scheduleWithFixedDelay(() -> execErrorJob(), 0, this.config.getErrorInterval(),
        TimeUnit.MILLISECONDS);
  }


  private void execJob() {
    try {
      for (Table table : tables) {

        String destination = table.getDestination();
        String database = table.getDatabase();
        String tableName = table.getTableName();
        log.info("exec Job {}/{}/{}", destination, database, tableName);
        LocalDateTime endTime = LocalDateTime.now()
            .minus(this.config.getExecBefore(), ChronoUnit.MILLIS);

        List<TableMonitorTask> waitDatas = this.taskManager.findWaitDatas(destination, endTime, 10);
        if (waitDatas.size() > 0) {
          log.info("wait data size=>{}", waitDatas.size());
          for (TableMonitorTask waitData : waitDatas) {
            log.info("process data {}/{}", waitData.getTableName(), waitData.getDataId());
            this.execCallback.callback(this.taskManager, destination, waitData);
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
      for (Table scanTable : tables) {
        taskManager.batchRetry(scanTable.getDestination(), RowStatus.RETRYING, 3,
            LocalDateTime.now());
      }

      LocalDateTime time = LocalDateTime.now()
          .minus(this.config.getRetryBefore(), ChronoUnit.MILLIS);
      for (Table scanTable : tables) {
        taskManager.batchRetry(scanTable.getDestination(), RowStatus.PROCESSING, 3, time);
      }
    } catch (Exception e) {
      log.error("execRetringJob error", e);
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
        taskManager.batchError(scanTable.getDestination(), 3, LocalDateTime.now());
      }
    } catch (Exception e) {
      log.error("execErrorJob error", e);
    }

  }

}