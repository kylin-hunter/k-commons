package io.github.kylinhunter.commons.jdbc.datasource.bean;

import java.util.Map;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-18 00:08
 */
@Data
public class DataSourceInfo {
  private String name;
  private boolean primary;
  private String driverClassName;
  private String jdbcUrl;
  private String username;
  private String password;

  private PoolInfo pool;
  private Map<String, String> dataSourceProperties;
}
