package io.github.kylinhunter.commons.generator.config.bean;

import io.github.kylinhunter.commons.collections.MapUtils;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class Templates {
  private Strategy strategy;
  private Map<String, Object> context = MapUtils.newHashMap();
  private List<Template> list;
}
