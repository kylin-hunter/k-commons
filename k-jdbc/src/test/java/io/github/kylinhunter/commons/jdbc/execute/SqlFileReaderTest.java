package io.github.kylinhunter.commons.jdbc.execute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class SqlFileReaderTest {

  @Test
  void read() {

    List<String> sqlLines = SqlFileReader.read(
        "io/github/kylinhunter/commons/jdbc/binlog/binlog-test.sql");

    System.out.println(sqlLines.get(0));
    assertEquals(1, sqlLines.size());
  }
}