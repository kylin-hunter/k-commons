package io.github.kylinhunter.commons.generator.config.bean;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import lombok.Data;

@Data
public class TemplateConfigs {
    private CommonStrategy strategy;
    private Map<String, Object> context = Maps.newHashMap();
    private List<TemplateConfig> list;
}
