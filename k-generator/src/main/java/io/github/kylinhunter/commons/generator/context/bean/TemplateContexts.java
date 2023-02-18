package io.github.kylinhunter.commons.generator.context.bean;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 00:57
 **/
@Data
public class TemplateContexts {
    private List<TemplateContext> templates = Lists.newArrayList();

    /**
     * @param templateContext templateInfo
     * @return void
     * @title addModule
     * @description
     * @author BiJi'an
     * @date 2023-02-18 21:34
     */
    public void add(TemplateContext templateContext) {
        templates.add(templateContext);
    }


    /**
     * @return java.util.Collection<io.github.kylinhunter.commons.generator.context.bean.TemplateInfo>
     * @title getAll
     * @description
     * @author BiJi'an
     * @date 2023-02-18 22:08
     */
    public Collection<TemplateContext> getAll() {
        return templates;
    }
}
