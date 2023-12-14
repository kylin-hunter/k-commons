package io.github.kylinhunter.commons.jdbc.execute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class SqlReaderTest {

  @Test
  void read() {

    List<String> sqlLines = SqlReader.readSqls(
        "io/github/kylinhunter/commons/jdbc/test_data.sql");

    System.out.println(sqlLines.get(0));
    assertEquals(8, sqlLines.size());
  }
}