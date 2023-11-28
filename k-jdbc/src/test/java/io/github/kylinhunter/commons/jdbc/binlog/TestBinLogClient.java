package io.github.kylinhunter.commons.jdbc.binlog;

class TestBinLogClient {

  public static void main(String[] args) {

    String jdbcUrl = "jdbc:mysql://localhost:3306/kp?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
    BinLogClient binLogClient = new BinLogClient(jdbcUrl, "root", "root");
    try {
      binLogClient.setBinlogFilename("binlog.000014");
      binLogClient.setBinlogPosition(4408);
      binLogClient.setServerId(2);
      binLogClient.start();
    } finally {
      binLogClient.disconnect();
    }
  }
}