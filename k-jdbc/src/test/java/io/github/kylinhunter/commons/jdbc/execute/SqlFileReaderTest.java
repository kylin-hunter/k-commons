package io.github.kylinhunter.commons.jdbc.execute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class SqlFileReaderTest {

  @Test
  void read() {

    List<String> sqlLines = SqlFileReader.read(
        "io/github/kylinhunter/commons/jdbc/binlog/binlog-test2.sql");

    System.out.println(sqlLines.get(0));
    System.out.println(sqlLines.get(1));
    assertEquals(2, sqlLines.size());
  }
}