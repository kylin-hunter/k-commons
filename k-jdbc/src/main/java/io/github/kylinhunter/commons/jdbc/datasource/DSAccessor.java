package io.github.kylinhunter.commons.jdbc.datasource;

/**
 * @author BiJi'an
 * @description
 * @date 2023-05-26 17:13
 */
public interface DSAccessor {

  String getDsName();

  void setDsName(String name);

  int getDsNo();

  void setDsNo(int no);
}
