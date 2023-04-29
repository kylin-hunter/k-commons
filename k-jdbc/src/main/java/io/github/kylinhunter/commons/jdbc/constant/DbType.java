package io.github.kylinhunter.commons.jdbc.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023/1/18
 */
@RequiredArgsConstructor
public enum DbType {
  MYSQL(ColumnParserType.MYSQL),
  ORACLE(ColumnParserType.ORACLE),
  SQL_SERVER(ColumnParserType.SQL_SERVER),
  OTHERS(ColumnParserType.MYSQL);

  @Getter private final ColumnParserType columnParserType;
}
