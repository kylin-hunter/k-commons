package io.github.kylinhunter.commons.generator.config.bean;

import java.util.Map;

import io.github.kylinhunter.commons.collections.MapUtils;

import lombok.Data;

@Data
public class Template {
    private boolean enabled = true;
    private String name;
    protected Map<String, Object> context = MapUtils.newHashMap();
    private TemplateStrategy strategy;

    /**
     * @param context context
     * @return void
     * @title putContext
     * @description
     * @author BiJi'an
     * @date 2023-02-18 23:15
     */
    public void putContext(Map<String, Object> context) {
        if (context != null) {
            this.context.putAll(context);
        }
    }

    /**
     * @param templates templateConfigs
     * @return void
     * @title merge
     * @description
     * @author BiJi'an
     * @date 2023-02-19 17:12
     */
    public void merge(Templates templates) {
        if (strategy == null) {
            this.strategy = new TemplateStrategy();
        }
        this.strategy.merge(templates.getStrategy());
        this.putContext(templates.getContext());

    }
}
