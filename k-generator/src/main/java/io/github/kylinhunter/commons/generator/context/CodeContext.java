package io.github.kylinhunter.commons.generator.context;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.github.kylinhunter.commons.generator.config.bean.Config;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 23:05
 **/
@Data
public class CodeContext {
    private Config config;
    private List<File> templateFiles = Lists.newArrayList();
    private Map<String, Object> contexts = Maps.newHashMap();

}
