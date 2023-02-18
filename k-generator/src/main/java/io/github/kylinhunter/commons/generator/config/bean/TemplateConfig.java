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
     * @param global global
     * @return void
     * @title merge
     * @description
     * @author BiJi'an
     * @date 2023-02-18 23:19
     */
    public void merge(Global global) {

        if (strategy == null) {
            this.strategy = new TemplateStrategy();
        }
        this.strategy.merge(global.getStrategy());
        this.putContext(global.getContext());
    }
}
