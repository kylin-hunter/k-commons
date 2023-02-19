package io.github.kylinhunter.commons.generator.config.bean;

import java.util.Map;

import com.google.common.collect.Maps;

import lombok.Data;

@Data
public class TemplateConfig {
    private boolean enabled = true;
    private String name;
    protected Map<String, Object> context = Maps.newHashMap();
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
     * @param templateConfigs templateConfigs
     * @return void
     * @title merge
     * @description
     * @author BiJi'an
     * @date 2023-02-19 17:12
     */
    public void merge(TemplateConfigs templateConfigs) {
        if (strategy == null) {
            this.strategy = new TemplateStrategy();
        }
        this.strategy.merge(templateConfigs.getStrategy());
        this.putContext(templateConfigs.getContext());

    }
}
