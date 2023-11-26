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
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.jdbc.config.url.JdbcUrl;
import io.github.kylinhunter.commons.jdbc.utils.JdbcUtils;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-25 02:51
 */
@Slf4j
public class BinLogClient {

  private final BinaryLogClient binaryLogClient;

  public BinLogClient(String jdbcUrl, String username, String password) {
    this(JdbcUtils.parse(jdbcUrl), username, password);
  }

  public BinLogClient(JdbcUrl jdbcUrl, String username, String password) {
    this(jdbcUrl.getHost(), jdbcUrl.getPort(), jdbcUrl.getDatabase(), username, password);
  }

  public BinLogClient(String hostname, int port, String schema, String username, String password) {
    binaryLogClient = new BinaryLogClient(hostname, port, schema, username, password);
  }

  public void setBinlogFilename(String binlogFilename) {
    this.binaryLogClient.setBinlogFilename(binlogFilename);
  }

  public void setBinlogPosition(long binlogPosition) {
    this.binaryLogClient.setBinlogPosition(binlogPosition);
  }

  /**
   * @title start
   * @description start
   * @author BiJi'an
   * @date 2023-11-27 01:32
   */
  public void start() {
    try {
      binaryLogClient.registerEventListener(new DefaultBinLogEventListener());
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

  public static void main(String[] args) {

    String jdbcUrl = "jdbc:mysql://localhost:3306/kp?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
    BinLogClient binLogClient = new BinLogClient(jdbcUrl, "root", "root");
    try {
      binLogClient.setBinlogFilename("binlog.000012");
      binLogClient.setBinlogPosition(0);
      binLogClient.start();
    } finally {
      binLogClient.disconnect();
    }
  }
}
