package io.github.kylinhunter.commons.jdbc.monitor.task;

import io.github.kylinhunter.commons.jdbc.exception.FastFailException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-24 18:12
 */
@Slf4j
public class TestRowListener extends AbstractRowListener {

  /**
   * @param database  database
   * @param tableName tableName
   * @param dataId    dataId
   * @title insert
   * @description insert
   * @author BiJi'an
   * @date 2023-12-28 17:08
   */
  @Override
  public void insert(String database, String tableName, String dataId) {
    log.info("inser to database:{},tableName:{},dataId:{}", database, tableName, dataId);
    // 模拟业务处理
    if (dataId.equals("1")) {
      log.info("模拟处理  数据1  insert 事件处理 成功 。。。。。。");
    } else if (dataId.equals("2")) {
      throw new FastFailException("模拟处理 数据2  的 insert 事件处理 快速失败 ，不再处理 。。。。。。");
    } else {
      throw new RuntimeException("模拟处理其他数据的 insert 事件，发生失败 ,默认会重试3次....");
    }
  }

  /**
   * @param database  database
   * @param tableName tableName
   * @param dataId    dataId
   * @title update
   * @description update
   * @author BiJi'an
   * @date 2023-12-28 17:08
   */
  @Override
  public void update(String database, String tableName, String dataId) {
    log.info("update to database:{},tableName:{},dataId:{}", database, tableName, dataId);
    // 模拟业务处理
    if (dataId.equals("1")) {
      log.info("模拟处理  数据1  insert 事件处理 成功 。。。。。。");
    } else if (dataId.equals("2")) {
      throw new FastFailException("模拟处理 数据2  的 update 事件处理 快速失败 ，不再处理 。。。。。。");
    } else {
      throw new RuntimeException("模拟处理其他数据的 update 事件，发生失败 ,默认会重试3次....");
    }
  }

  /**
   * @param database  database
   * @param tableName tableName
   * @param dataId    dataId
   * @title delete
   * @description delete
   * @author BiJi'an
   * @date 2023-12-28 17:08
   */
  @Override
  public void delete(String database, String tableName, String dataId) {
    log.info("delete to database:{},tableName:{},dataId:{}", database, tableName, dataId);
    // 模拟业务处理
    if (dataId.equals("1")) {
      log.info("模拟处理  数据1  insert 事件处理 成功 。。。。。。");
    } else if (dataId.equals("2")) {
      throw new FastFailException("模拟处理 数据2  的 update 事件处理 快速失败 ，不再处理 。。。。。。");
    } else {
      throw new RuntimeException("模拟处理其他数据的 delete 事件，发生失败 ,默认会重试3次....");
    }
  }
}