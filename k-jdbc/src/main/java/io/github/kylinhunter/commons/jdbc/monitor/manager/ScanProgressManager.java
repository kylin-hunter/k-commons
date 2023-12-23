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
package io.github.kylinhunter.commons.jdbc.monitor.manager;

import io.github.kylinhunter.commons.exception.embed.UnsupportedException;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.dao.AbstractDatabaseVisitor;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.ScanProgressDAO;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.ScanProgress;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.ScanRecord;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.imp.MysqlScanProgressDAO;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.ScanTable;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-09 22:48
 */
@Slf4j
public class ScanProgressManager extends AbstractDatabaseVisitor {

  private ScanProgressDAO scanProgressDAO;

  public ScanProgressManager() {
    super(null, true);
    init();
  }

  public ScanProgressManager(DataSource dataSource) {
    super(dataSource, false);
    init();
  }

  /**
   * @title init
   * @description init
   * @author BiJi'an
   * @date 2023-12-16 00:11
   */
  private void init() {
    if (this.dbType == DbType.MYSQL) {
      this.scanProgressDAO = new MysqlScanProgressDAO(dataSource);
    } else {
      throw new UnsupportedException("unsupported dbType=" + dbType);
    }
  }

  /**
   * @title createScanProgress
   * @description createScanProgress
   * @author BiJi'an
   * @date 2023-12-10 21:17
   */
  public ScanProgress getLatestScanProgress(ScanTable scanTable) {

    ScanProgress progress =
        this.scanProgressDAO.findById(scanTable.getServerId(), scanTable.getTableName());
    if (progress == null) {
      progress =
          new ScanProgress(
              scanTable.getServerId(),
              scanTable.getTableName(),
              scanTable.getDestination(),
              scanTable.getInitScanTime(),
              scanTable.getInitScanId());
      this.scanProgressDAO.save(progress);
    }
    return progress;
  }

  /**
   * @param serverId  serverId
   * @param tableName tableName
   * @param last      last
   * @title update
   * @description update
   * @author BiJi'an
   * @date 2023-12-16 20:39
   */
  public void update(String serverId, String tableName, ScanRecord last) {
    ScanProgress scanProgress = new ScanProgress(serverId, tableName, last.getTime(), last.getId());
    this.scanProgressDAO.update(scanProgress);
  }

  /**
   * @title ensureTableExists
   * @description ensureTableExists
   * @author BiJi'an
   * @date 2023-12-09 00:16
   */
  public void ensureTableExists() {
    this.scanProgressDAO.ensureTableExists();
  }

  /**
   * @param id id
   * @title delete
   * @description delete
   * @author BiJi'an
   * @date 2023-12-16 20:41
   */
  public void delete(String id, String tableName) {
    this.scanProgressDAO.delete(id, tableName);
  }
}
