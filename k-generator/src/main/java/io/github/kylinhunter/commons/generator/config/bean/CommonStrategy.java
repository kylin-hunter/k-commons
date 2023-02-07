package io.github.kylinhunter.commons.generator.config.bean;

import java.util.Map;

import com.google.common.collect.Maps;

import io.github.kylinhunter.commons.io.file.FileExtensions;
import lombok.Data;

@Data
public class CommonStrategy {
    private String packagePattern;
    private Map<String, String> variables = Maps.newHashMap();
    private boolean serializable = false;
    private boolean lombok = false;
    private boolean lombokChainModel = false;
    private String encoding = "UTF-8";
    private String extension = FileExtensions.VM;
}
