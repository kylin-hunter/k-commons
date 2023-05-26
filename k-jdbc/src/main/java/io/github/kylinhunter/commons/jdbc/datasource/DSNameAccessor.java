package io.github.kylinhunter.commons.jdbc.datasource;

/**
 * @author BiJi'an
 * @description
 * @date 2023-05-26 17:13
 */
public interface DSNameAccessor {

  String getDsName();

  void setDsName(String name);
}
