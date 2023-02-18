package io.github.kylinhunter.commons.generator.config.bean;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-23 00:01
 **/
@Data
public class Global {
    private String templatePath;
    private String outputPath;
    protected String databaseName;
    private GlobalStrategy strategy;
    private Map<String, Object> context = Maps.newHashMap();


}
