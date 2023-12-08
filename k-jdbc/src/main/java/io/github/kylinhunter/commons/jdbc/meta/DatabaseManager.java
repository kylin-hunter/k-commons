package io.github.kylinhunter.commons.jdbc.meta;

import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import javax.sql.DataSource;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-08 23:36
 */
public interface DatabaseManager {

  /**
   * @return javax.sql.DataSource
   * @title getDataSource
   * @description getDataSource
   * @author BiJi'an
   * @date 2023-12-08 23:37
   */
  DataSource getDataSource();

  /**
   * @return javax.sql.DataSource
   * @title getDefaultDataSource
   * @description getDefaultDataSource
   * @author BiJi'an
   * @date 2023-12-08 23:37
   */
  DataSource getDefaultDataSource();

  /**
   * @return io.github.kylinhunter.commons.jdbc.execute.SqlExecutor
   * @title getSqlExecutor
   * @description getSqlExecutor
   * @author BiJi'an
   * @date 2023-12-08 23:37
   */
  SqlExecutor getSqlExecutor();

  /**
   * @return io.github.kylinhunter.commons.jdbc.execute.SqlExecutor
   * @title getDefaultSqlExecutor
   * @description getDefaultSqlExecutor
   * @author BiJi'an
   * @date 2023-12-08 23:37
   */
  SqlExecutor getDefaultSqlExecutor();

}