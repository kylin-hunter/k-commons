package io.github.kylinhunter.commons.jdbc.meta;

import io.github.kylinhunter.commons.jdbc.datasource.DataSourceUtils;
import javax.sql.DataSource;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-29 22:34
 */
public class AbstractDatabaseManager {

  protected DataSource dataSource;

  public AbstractDatabaseManager() {
    this.dataSource = DataSourceUtils.getDefaultDataSource();
  }

}