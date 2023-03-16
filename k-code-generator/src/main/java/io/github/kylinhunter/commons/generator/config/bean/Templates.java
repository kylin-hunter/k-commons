package io.github.kylinhunter.commons.generator.config.bean;

import java.util.List;
import java.util.Map;

import io.github.kylinhunter.commons.collections.MapUtils;

import lombok.Data;

@Data
public class Templates {
    private Strategy strategy;
    private Map<String, Object> context = MapUtils.newHashMap();
    private List<Template> list;
}
