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
import java.io.IOException;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-25 02:51
 */
public class BinLogClient {

  BinaryLogClient binaryLogClient;

  public BinLogClient(String hostname, int port, String schema, String username, String password) {
    binaryLogClient = new BinaryLogClient(hostname, port, username, password);

    binaryLogClient.setBinlogFilename("binlog.000012");
    binaryLogClient.setBinlogPosition(0);

    binaryLogClient.registerEventListener(new DefaultBinLogEventListener());
  }

  public void start() {
    try {
      binaryLogClient.connect();
    } catch (IOException e) {
      throw new InitException("start error", e);
    }
  }

  public static void main(String[] args) {
    new Thread(
            () -> {
              BinLogClient binLogClient = new BinLogClient("localhost", 3306, "kp", "root", "root");
              binLogClient.start();
            })
        .start();
  }
}
