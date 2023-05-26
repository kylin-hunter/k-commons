package io.github.kylinhunter.commons.jdbc.meta.parser.imp;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.jdbc.meta.parser.ColumnParser;
import io.github.kylinhunter.commons.sys.KGenerated;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 */
@C
@KGenerated
public class ColumnParserOracle extends ColumnParserMysql {
  /**
   * @see ColumnParser#calJavaClass(int)
   */
  public Class<?> calJavaClass(int dataType) {
    return super.calJavaClass(dataType);
  }
}
