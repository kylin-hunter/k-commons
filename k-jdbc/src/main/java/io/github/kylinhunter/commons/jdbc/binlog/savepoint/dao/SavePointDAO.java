package io.github.kylinhunter.commons.jdbc.binlog.savepoint.dao;

import io.github.kylinhunter.commons.jdbc.binlog.savepoint.dao.entity.SavePoint;
import io.github.kylinhunter.commons.jdbc.config.url.JdbcUrl;

public interface SavePointDAO {

  /**
   * @param savePoint savePoint
   * @title save
   * @description save
   * @author BiJi'an
   * @date 2023-12-09 00:14
   */
  void save(SavePoint savePoint);

  /**
   * @return io.github.kylinhunter.commons.jdbc.binlog.savepoint.dao.entity.SavePoint
   * @title get
   * @description get
   * @author BiJi'an
   * @date 2023-12-09 00:14
   */
  SavePoint get();

  /**
   * @param savePoint savePoint
   * @title update
   * @description update
   * @author BiJi'an
   * @date 2023-12-09 00:14
   */
  void update(SavePoint savePoint);

  /**
   * @return io.github.kylinhunter.commons.jdbc.config.url.JdbcUrl
   * @title getJdbcUrl
   * @description getJdbcUrl
   * @author BiJi'an
   * @date 2023-12-09 00:14
   */
  JdbcUrl getJdbcUrl();

  /**
   * @title ensureTableExists
   * @description ensureTableExists
   * @author BiJi'an
   * @date 2023-12-09 00:14
   */
  void ensureTableExists();


}
