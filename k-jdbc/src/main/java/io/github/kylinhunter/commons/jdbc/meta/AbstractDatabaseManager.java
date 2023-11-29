package io.github.kylinhunter.commons.jdbc.meta;

import io.github.kylinhunter.commons.jdbc.datasource.DataSourceUtils;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-29 22:34
 */
public class AbstractDatabaseManager {

  protected DataSource defaultDataSource;

  protected QueryRunner defaultQueryRunner;


  public AbstractDatabaseManager() {
    this.defaultDataSource = DataSourceUtils.getDefaultDataSource();
    this.defaultQueryRunner = new QueryRunner(this.defaultDataSource);
  }

  public AbstractDatabaseManager(DataSource defaultDataSource) {
    this.defaultDataSource = defaultDataSource;
    this.defaultQueryRunner = new QueryRunner(this.defaultDataSource);

  }
}