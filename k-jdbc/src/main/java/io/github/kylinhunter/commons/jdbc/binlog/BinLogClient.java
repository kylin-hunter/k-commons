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
import io.github.kylinhunter.commons.exception.check.ThrowChecker;
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.dao.entity.SavePoint;
import io.github.kylinhunter.commons.jdbc.config.url.JdbcUrl;
import io.github.kylinhunter.commons.jdbc.utils.JdbcUtils;
import java.io.IOException;
import lombok.Setter;
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

  @Setter
  private SavePointManager savePointManager;

  public BinLogClient(String jdbcUrl, String username, String password) {
    this(JdbcUtils.parse(jdbcUrl), username, password);
  }


  public BinLogClient(String hostname, int port, String schema, String username, String password) {
    this(new JdbcUrl(hostname, port, schema), username, password);
  }

  public BinLogClient(JdbcUrl jdbcUrl, String username, String password) {
    this.jdbcUrl = jdbcUrl;
    this.binaryLogClient = new BinaryLogClient(jdbcUrl.getHost(), jdbcUrl.getPort(),
        jdbcUrl.getDatabase(), username, password);
  }

  /**
   * @param binlogFilename binlogFilename
   * @title setBinlogFilename
   * @description setBinlogFilename
   * @author BiJi'an
   * @date 2023-11-28 23:24
   */
  public void setBinlogFilename(String binlogFilename) {
    this.binaryLogClient.setBinlogFilename(binlogFilename);
  }

  /**
   * @param binlogPosition binlogPosition
   * @title setBinlogPosition
   * @description setBinlogPosition
   * @author BiJi'an
   * @date 2023-11-28 23:24
   */
  public void setBinlogPosition(long binlogPosition) {
    this.binaryLogClient.setBinlogPosition(binlogPosition);
  }

  /**
   * @param serverId serverId
   * @title setServerId
   * @description setServerId
   * @author BiJi'an
   * @date 2023-11-28 23:24
   */
  public void setServerId(long serverId) {
    this.binaryLogClient.setServerId(serverId);
  }

  /**
   * @title start
   * @description start
   * @author BiJi'an
   * @date 2023-11-27 01:32
   */
  public void start() {
    try {
      ThrowChecker.checkNotNull(savePointManager, "savePointManager can't be null");
      savePointManager.init(this.jdbcUrl);

      SavePoint savePoint = savePointManager.get();
      if (savePoint != null && savePoint.getPosition() >= 0) {
        binaryLogClient.setBinlogFilename(savePoint.getName());
        binaryLogClient.setBinlogPosition(savePoint.getPosition());
      }
      DefaultBinLogEventListener eventListener = new DefaultBinLogEventListener();
      eventListener.setSavePointManager(savePointManager);
      binaryLogClient.registerEventListener(eventListener);

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
