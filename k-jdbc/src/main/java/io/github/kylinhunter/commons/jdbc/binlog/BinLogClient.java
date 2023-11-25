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
    new Thread(() -> {
      BinLogClient binLogClient = new BinLogClient("localhost", 3306, "kp", "root", "root");
      binLogClient.start();
    }).start();
  }


}