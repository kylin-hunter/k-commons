package io.github.kylinhunter.commons.jdbc.meta.parser;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 */
public interface ColumnParser {
  Class<?> calJavaClass(int dataType);
}
