package io.github.kylinhunter.commons.generator.context.bean;

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
    private List<TemplateContext> templateContexts = Lists.newArrayList();

    private List<ModuleContext> moduleContexts = Lists.newArrayList();

    private Map<String, Object> contexts = Maps.newHashMap();

    public void addTemplateContext(TemplateContext templateContext) {
        this.templateContexts.add(templateContext);
    }

    public void addModuleContext(ModuleContext moduleContext) {
        this.moduleContexts.add(moduleContext);
    }

}
