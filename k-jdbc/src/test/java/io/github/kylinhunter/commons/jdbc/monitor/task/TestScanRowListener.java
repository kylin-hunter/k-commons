package io.github.kylinhunter.commons.jdbc.monitor.task;

import io.github.kylinhunter.commons.jdbc.exception.FastFailException;
import io.github.kylinhunter.commons.jdbc.monitor.bean.Table;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-24 18:12
 */
@Slf4j
public class TestScanRowListener extends AbstractScanRowListener {

  /**
   * @param table  table
   * @param dataId dataId
   * @title insert
   * @description insert
   * @author BiJi'an
   * @date 2023-12-30 17:08
   */
  @Override
  public void insert(Table table, String dataId) {
    log.info("insert to tableName:{},dataId:{}", table.getTableName(), dataId);
    // 模拟业务处理
    if (dataId.equals("1")) {
      log.info("模拟处理  数据1  insert 事件处理 成功 。。。。。。");
    } else if (dataId.equals("2")) {
      throw new FastFailException("模拟处理 数据2  的 insert 事件处理 快速失败 ，不再处理 。。。。。。");
    } else {
      throw new RuntimeException("模拟处理其他数据的 insert 事件，发生失败 ,默认会重试1次....");
    }
  }

  /**
   * @param table  table
   * @param dataId dataId
   * @title update
   * @description update
   * @author BiJi'an
   * @date 2023-12-30 17:08
   */
  @Override
  public void update(Table table, String dataId) {
    log.info("update to tableName:{},dataId:{}", table.getTableName(), dataId);
    // 模拟业务处理
    if (dataId.equals("1")) {
      log.info("模拟处理  数据1  update 事件处理 成功 。。。。。。");
    } else if (dataId.equals("2")) {
      throw new FastFailException("模拟处理 数据2  的 update 事件处理 快速失败 ，不再处理 。。。。。。");
    } else {
      throw new RuntimeException("模拟处理其他数据的 update 事件，发生失败 ,默认会重试1次....");
    }
  }

  /**
   * @param table  table
   * @param dataId dataId
   * @title delete
   * @description delete
   * @author BiJi'an
   * @date 2023-12-30 17:08
   */
  @Override
  public void delete(Table table, String dataId) {
    log.info("delete to tableName:{},dataId:{}", table.getTableName(), dataId);
    // 模拟业务处理
    if (dataId.equals("1")) {
      log.info("模拟处理  数据1  delete 事件处理 成功 。。。。。。");
    } else if (dataId.equals("2")) {
      throw new FastFailException("模拟处理 数据2  的 delete 事件处理 快速失败 ，不再处理 。。。。。。");
    } else {
      throw new RuntimeException("模拟处理其他数据的 delete 事件，发生失败 ,默认会重试1次....");
    }
  }
}