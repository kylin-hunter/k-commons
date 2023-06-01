package io.github.kylinhunter.commons.generator.config.bean;

import io.github.kylinhunter.commons.collections.MapUtils;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Module {
  @EqualsAndHashCode.Include private String name;
  protected Database database;
  private Map<String, Object> context = MapUtils.newHashMap();
  private Table table;

  public void merge(Modules modules) {
    this.database = modules.database;
  }
}
