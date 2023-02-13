package io.github.kylinhunter.commons.generator.config.bean;

import java.util.Map;

import com.google.common.collect.Maps;

import lombok.Data;

@Data
public class CommonStrategy {
    protected String packagePattern;
    protected Map<String, String> variables = Maps.newHashMap();
    protected boolean serializable = false;
    protected boolean lombok = false;
    protected boolean lombokChainModel = false;
    protected String encoding;
    protected String extension;
}
