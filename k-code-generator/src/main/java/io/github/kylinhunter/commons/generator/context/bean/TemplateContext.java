package io.github.kylinhunter.commons.generator.context.bean;

import java.util.Map;

import io.github.kylinhunter.commons.collections.MapUtils;

import io.github.kylinhunter.commons.generator.config.bean.Template;
import io.github.kylinhunter.commons.generator.context.bean.clazz.ClassInfo;
import io.github.kylinhunter.commons.generator.context.bean.module.ModuleInfo;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 00:57
 **/
@Data
public class TemplateContext {
    private final ModuleInfo moduleInfo;
    private final Template template;
    private ClassInfo classInfo = new ClassInfo();
    private Map<String, Object> context = MapUtils.newHashMap();

    /**
     * @param contextData contextData
     * @return void
     * @title addAll
     * @description
     * @author BiJi'an
     * @date 2023-02-19 02:43
     */
    public void putContext(Map<String, ?> contextData) {
        if (contextData != null) {
            this.context.putAll(contextData);
        }
    }

    /**
     * @param key    key
     * @param object object
     * @return void
     * @title put
     * @description
     * @author BiJi'an
     * @date 2023-02-19 22:17
     */
    public void putContext(String key, Object object) {
        this.context.put(key, object);
    }

}
