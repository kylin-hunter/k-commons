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
import io.github.kylinhunter.commons.jdbc.monitor.dao.ScanRecordDAO;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.ScanProgress;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.ScanRecord;
import io.github.kylinhunter.commons.jdbc.monitor.dao.imp.MysqlScanRecordDAO;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.ScanTable;
import java.util.List;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-09 22:48
 */
@Slf4j
public class ScanRecordManager extends AbstractDatabaseVisitor {

  private ScanRecordDAO scanRecordDAO;

  public ScanRecordManager() {
    super(null, true);
    init();
  }

  public ScanRecordManager(DataSource dataSource) {
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
      this.scanRecordDAO = new MysqlScanRecordDAO(dataSource);
    } else {
      throw new UnsupportedException("unsupported dbType=" + dbType);
    }
  }

  /**
   * @param scanTable scanTable
   * @param scanProgress scanProgress
   * @return java.util.List<io.github.kylinhunter.commons.jdbc.monitor.dao.entity.ScanRecord>
   * @title scanSameTime
   * @description scanSameTime
   * @author BiJi'an
   * @date 2023-12-16 23:59
   */
  public List<ScanRecord> scanSameTime(ScanTable scanTable, ScanProgress scanProgress) {
    return this.scanRecordDAO.scanSameTime(
        scanTable, scanProgress.getNextScanTime(), scanProgress.getLastScanId());
  }

  /**
   * @param scanTable tableScanConfig
   * @param scanProgress scanProgress
   * @return java.util.List<io.github.kylinhunter.commons.jdbc.monitor.dao.entity.ScanRecord>
   * @title scanNextTime
   * @description scanNextTime
   * @author BiJi'an
   * @date 2023-12-16 22:49
   */
  public List<ScanRecord> scanNextTime(ScanTable scanTable, ScanProgress scanProgress) {
    return this.scanRecordDAO.scanNextTime(scanTable, scanProgress.getNextScanTime());
  }
}
