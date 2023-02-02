package io.github.kylinhunter.commons.generator.config.bean;

import java.util.Map;

import com.google.common.collect.Maps;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-23 00:01
 **/
@Data
public class GlobalConfig {
    private String templatePath;
    private String outputPathPath;
    private Map<String, String> variables = Maps.newHashMap();

}
