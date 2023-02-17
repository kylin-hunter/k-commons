package io.github.kylinhunter.commons.generator.context.bean;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 23:05
 **/
@Data
public class CodeContext {
    private List<TemplateContext> templateContexts = Lists.newArrayList();

    private List<ModuleContext> moduleContexts = Lists.newArrayList();

    private Map<String, Object> contexts = Maps.newHashMap();

    /**
     * @param templateContext templateContext
     * @return void
     * @title addTemplateContext
     * @description
     * @author BiJi'an
     * @date 2023-02-16 23:42
     */
    public void addTemplateContext(TemplateContext templateContext) {
        this.templateContexts.add(templateContext);
    }

    /**
     * @param moduleContext moduleContext
     * @return void
     * @title addModuleContext
     * @description
     * @author BiJi'an
     * @date 2023-02-16 23:42
     */
    public void addModuleContext(ModuleContext moduleContext) {
        this.moduleContexts.add(moduleContext);
    }

}
