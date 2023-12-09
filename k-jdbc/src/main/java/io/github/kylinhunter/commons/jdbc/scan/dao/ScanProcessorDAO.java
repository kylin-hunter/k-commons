package io.github.kylinhunter.commons.jdbc.scan.dao;

import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.scan.dao.entity.ScanProcessor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-09 22:50
 */
public interface ScanProcessorDAO {

  /**
   * @param tableName tableName
   * @return io.github.kylinhunter.commons.jdbc.scan.dao.entity.ScanProcessor
   * @title findById
   * @description findById
   * @author BiJi'an
   * @date 2023-12-09 00:13
   */
  ScanProcessor findById(String tableName, String bizTable, String dataId);

  /**
   * @param scanProcessor scanProcessor
   * @title save
   * @description save
   * @author BiJi'an
   * @date 2023-12-09 00:13
   */
  void save(String tableName, ScanProcessor scanProcessor);

  /**
   * @param scanProcessor scanProcessor
   * @title update
   * @description update
   * @author BiJi'an
   * @date 2023-12-09 00:14
   */
  void update(String tableName, ScanProcessor scanProcessor);


  /**
   * @return io.github.kylinhunter.commons.jdbc.execute.SqlExecutor
   * @title getSqlExecutor
   * @description getSqlExecutor
   * @author BiJi'an
   * @date 2023-12-09 00:14
   */
  SqlExecutor getSqlExecutor();

  /**
   * @title ensureTableExists
   * @description ensureTableExists
   * @author BiJi'an
   * @date 2023-12-09 00:16
   */
  void ensureTableExists(String tableName);

  /**
   * @param tableName tableName
   * @title clean
   * @description clean
   * @author BiJi'an
   * @date 2023-12-09 14:44
   */
  void clean(String tableName, String bizTableName);
}