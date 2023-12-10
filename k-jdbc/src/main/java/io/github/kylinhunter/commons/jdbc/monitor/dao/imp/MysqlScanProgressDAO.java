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
package io.github.kylinhunter.commons.jdbc.monitor.dao.imp;

import io.github.kylinhunter.commons.jdbc.execute.SqlReader;
import io.github.kylinhunter.commons.jdbc.meta.AbstractDatabaseManager;
import io.github.kylinhunter.commons.jdbc.meta.table.MysqlTableReader;
import io.github.kylinhunter.commons.jdbc.meta.table.TableReader;
import io.github.kylinhunter.commons.jdbc.monitor.dao.ScanProgressDAO;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.ScanProgress;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-03 19:50
 */
public class MysqlScanProgressDAO extends AbstractDatabaseManager implements ScanProgressDAO {

  private static final String INIT_SQL =
      "io/github/kylinhunter/commons/jdbc/scan/scan-mysql-scan_progress.sql";
  private static final String TABLE_SCAN_PROGRESS = "k_table_scan_progress";

  private static final String INSERT_SQL =
      "insert into "
          + TABLE_SCAN_PROGRESS
          + " (id, save_destination, next_scan_time,last_scan_id) values(?,?,?,?)";

  private static final String UPDATE_SQL =
      "update " + TABLE_SCAN_PROGRESS + " set next_scan_time=?,last_scan_id=? where id=?";

  private static final String DELETE_SQL = "delete from " + TABLE_SCAN_PROGRESS + " where id=?";

  private static final String SELECT_SQL =
      "select  id, save_destination saveDestination, "
          + " next_scan_time nextScanTime,last_scan_id lastScanId  from "
          + TABLE_SCAN_PROGRESS
          + " where id=?";
  private final BeanHandler<ScanProgress> beanHandler = new BeanHandler<>(ScanProgress.class);

  private final TableReader tableReader;

  public MysqlScanProgressDAO(boolean dbConfigEnabled) {

    this(null, dbConfigEnabled);
  }

  public MysqlScanProgressDAO(DataSource dataSource, boolean dbConfigEnabled) {
    super(dataSource, dbConfigEnabled);
    this.tableReader = new MysqlTableReader(dataSource, dbConfigEnabled);
  }

  public void save(ScanProgress scanProgress) {

    this.getSqlExecutor()
        .execute(
            INSERT_SQL,
            scanProgress.getId(),
            scanProgress.getSaveDestination(),
            scanProgress.getNextScanTime(),
            scanProgress.getLastScanId());
  }

  @Override
  public void update(ScanProgress scanProgress) {
    this.getSqlExecutor()
        .execute(
            UPDATE_SQL,
            scanProgress.getNextScanTime(),
            scanProgress.getLastScanId(),
            scanProgress.getId());
  }

  /**
   * @param id id
   * @return io.github.kylinhunter.commons.jdbc.scan.bean.TableScanProgress
   * @title getTableScanProgress
   * @description getTableScanProgress
   * @author BiJi'an
   * @date 2023-12-09 22:42
   */
  public ScanProgress findById(String id) {

    return this.getSqlExecutor().query(SELECT_SQL, beanHandler, id);
  }

  /**
   * @title ensureTableExists
   * @description ensureTableExists
   * @author BiJi'an
   * @date 2023-12-09 00:16
   */
  @Override
  public void ensureTableExists() {
    boolean exist = this.tableReader.exist(this.getDataSource(), "", TABLE_SCAN_PROGRESS);
    if (!exist) {
      List<String> sqlLines = SqlReader.read(INIT_SQL);
      this.getSqlExecutor().execute(sqlLines, true);
    }
  }

  @Override
  public void delete(String id) {
    this.getSqlExecutor().execute(DELETE_SQL, id);
  }
}
