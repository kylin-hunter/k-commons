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
package io.github.kylinhunter.commons.jdbc.binlog;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.jdbc.binlog.dao.entity.SavePoint;
import io.github.kylinhunter.commons.jdbc.binlog.listener.BinLogEventListener;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import io.github.kylinhunter.commons.jdbc.url.JdbcUrl;
import java.io.IOException;
import java.util.List;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description the client reading bin log
 * @date 2023-11-25 02:51
 */
@Slf4j
public class BinLogClient {

  private final BinaryLogClient binaryLogClient;
  private final JdbcUrl jdbcUrl;

  private final DataSource dataSource;
  private final BinLogConfig binLogConfig;
  private final List<BinLogEventListener> eventListeners = ListUtils.newArrayList();

  public BinLogClient(BinLogConfig config) {
    this.binLogConfig = config;
    this.jdbcUrl = config.getJdbcUrl();
    String username = config.getUsername();
    String password = config.getPassword();
    this.binaryLogClient =
        new BinaryLogClient(
            jdbcUrl.getHost(), jdbcUrl.getPort(), jdbcUrl.getDatabase(), username, password);
    this.dataSource = DataSourceManager.createDataSource(config.getUrl(), username, password);
  }

  public void addBinLogEventListener(BinLogEventListener binLogEventListener) {
    this.eventListeners.add(binLogEventListener);
  }

  /**
   * @title start
   * @description start
   * @author BiJi'an
   * @date 2023-11-27 01:32
   */
  public void start() {
    try {
      SavePointManager savePointManager = binLogConfig.getSavePointManager();
      savePointManager.init(this.jdbcUrl);
      binaryLogClient.setBinlogFilename(binLogConfig.getBinlogFilename());
      binaryLogClient.setBinlogPosition(binLogConfig.getBinlogPosition());
      SavePoint savePoint = savePointManager.get();
      if (savePoint != null && savePoint.getPosition() >= 0) {
        binaryLogClient.setBinlogFilename(savePoint.getName());
        binaryLogClient.setBinlogPosition(savePoint.getPosition());
      }
      for (BinLogEventListener eventListener : this.eventListeners) {
        eventListener.setSavePointManager(savePointManager);
        eventListener.init(this.dataSource);
        binaryLogClient.registerEventListener(eventListener);
      }

      binaryLogClient.connect();
    } catch (IOException e) {
      throw new InitException("start error", e);
    }
  }

  /**
   * @title disconnect
   * @description disconnect
   * @author BiJi'an
   * @date 2023-11-27 01:32
   */
  public void disconnect() {
    try {
      binaryLogClient.disconnect();
    } catch (Exception e) {
      log.error("disconnect error", e);
    }
  }
}
