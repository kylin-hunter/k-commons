package io.github.kylinhunter.commons.jdbc.scan.dao;

import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.scan.dao.entity.ScanProgress;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-09 22:50
 */
public interface ScanProgressDAO {

  /**
   * @param id id
   * @return io.github.kylinhunter.commons.jdbc.scan.dao.entity.ScanProgress
   * @title findById
   * @description findById
   * @author BiJi'an
   * @date 2023-12-09 00:13
   */
  ScanProgress findById(String id);

  /**
   * @param scanProgress scanProgress
   * @title save
   * @description save
   * @author BiJi'an
   * @date 2023-12-09 00:13
   */
  void save(ScanProgress scanProgress);

  /**
   * @param scanProgress scanProgress
   * @title update
   * @description update
   * @author BiJi'an
   * @date 2023-12-09 00:14
   */
  void update(ScanProgress scanProgress);


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
  void ensureTableExists();


}