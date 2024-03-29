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
package io.github.kylinhunter.commons.jdbc.monitor.manager.dao.imp;

import io.github.kylinhunter.commons.jdbc.dao.AbsctractBasicDAO;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.ScanRecordDAO;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.ScanRecord;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.ScanTable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-03 19:50
 */
public class MysqlScanRecordDAO extends AbsctractBasicDAO implements ScanRecordDAO {

  private static final String SAME_SQL =
      "select %s as id ,%s as time from  %s "
          + " where %s = ? and %s<? and %s > ? order by %s asc, %s asc limit ?";

  private static final String NEXT_SQL =
      "select %s as id ,%s as time from %s "
          + " where %s > ?  and %s<? order by %s asc, %s asc limit ?";
  private final BeanListHandler<ScanRecord> beanHandler = new BeanListHandler<>(ScanRecord.class);

  public MysqlScanRecordDAO(DataSource dataSource) {
    super(dataSource, false);
  }

  /**
   * @param config config
   * @param startTime startTime
   * @param lastScanId lastScanId
   * @return java.util.List<io.github.kylinhunter.commons.jdbc.monitor.dao.entity.ScanRecord>
   * @title scanSameTime
   * @description scanSameTime
   * @author BiJi'an
   * @date 2023-12-17 14:36
   */
  public List<ScanRecord> scanSameTime(
      ScanTable config, LocalDateTime startTime, String lastScanId) {
    String sql =
        String.format(
            SAME_SQL,
            config.getPkColName(),
            config.getTableTimeName(),
            config.getTableName(),
            config.getTableTimeName(),
            config.getTableTimeName(),
            config.getPkColName(),
            config.getTableTimeName(),
            config.getPkColName());
    LocalDateTime endTime = LocalDateTime.now().minus(3, ChronoUnit.SECONDS);

    long limit = config.getScanLimit();
    return sqlExecutor.query(sql, beanHandler, startTime, endTime, lastScanId, limit);
  }

  /**
   * @param config config
   * @param startTime startTime
   * @return java.util.List<io.github.kylinhunter.commons.jdbc.monitor.dao.entity.ScanRecord>
   * @title scanNextTime
   * @description scanNextTime
   * @author BiJi'an
   * @date 2023-12-16 22:49
   */
  public List<ScanRecord> scanNextTime(ScanTable config, LocalDateTime startTime) {

    String sql =
        String.format(
            NEXT_SQL,
            config.getPkColName(),
            config.getTableTimeName(),
            config.getTableName(),
            config.getTableTimeName(),
            config.getTableTimeName(),
            config.getTableTimeName(),
            config.getPkColName());
    LocalDateTime endTime = LocalDateTime.now().minus(3, ChronoUnit.SECONDS);

    return sqlExecutor.query(sql, beanHandler, startTime, endTime, config.getScanLimit());
  }
}
