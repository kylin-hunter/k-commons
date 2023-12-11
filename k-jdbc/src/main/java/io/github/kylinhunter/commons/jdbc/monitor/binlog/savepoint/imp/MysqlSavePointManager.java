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
package io.github.kylinhunter.commons.jdbc.monitor.binlog.savepoint.imp;

import io.github.kylinhunter.commons.jdbc.exception.JdbcException;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.SavePoint;
import io.github.kylinhunter.commons.jdbc.monitor.dao.imp.MysqlSavePointDAO;
import io.github.kylinhunter.commons.jdbc.url.JdbcUrl;
import java.util.Objects;
import javax.sql.DataSource;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-28 23:36
 */
public class MysqlSavePointManager implements SavePointManager {

  private final MysqlSavePointDAO mysqlSavePointDAO;

  public MysqlSavePointManager() {
    this.mysqlSavePointDAO = new MysqlSavePointDAO(null, true);
  }

  public MysqlSavePointManager(DataSource dataSource) {
    this.mysqlSavePointDAO = new MysqlSavePointDAO(dataSource, false);
  }

  @Override
  public void reset() {
    this.mysqlSavePointDAO.update(this.getDefaultSavePoint());
  }

  @Override
  public void save(SavePoint savePoint) {
    this.mysqlSavePointDAO.update(savePoint);
  }

  @Override
  public SavePoint get() {
    return this.mysqlSavePointDAO.get();
  }

  @Override
  public void init(JdbcUrl jdbcUrlBinLog) {
    JdbcUrl jdbcUrlSavePoint = this.mysqlSavePointDAO.getJdbcUrl();
    Objects.requireNonNull(jdbcUrlBinLog);
    Objects.requireNonNull(jdbcUrlSavePoint);

    if (jdbcUrlBinLog.equals(jdbcUrlSavePoint)) {
      throw new JdbcException("save point jdbc can't be equal to binlog jdbc");
    }
    this.mysqlSavePointDAO.ensureTableExists();
    SavePoint savePoint = this.get();
    if (savePoint == null) {
      this.mysqlSavePointDAO.save(this.getDefaultSavePoint());
    }
  }
}