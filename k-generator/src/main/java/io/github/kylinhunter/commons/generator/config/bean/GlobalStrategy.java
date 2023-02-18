package io.github.kylinhunter.commons.generator.config.bean;

import java.util.Map;

import com.google.common.collect.Maps;

import lombok.Data;

@Data
public class GlobalStrategy {
    protected String packagePattern;
    protected String encoding;
    protected String extension;
}
