package io.github.kylinhunter.commons.generator.context.bean;

import java.util.Map;

import com.google.common.collect.Maps;

import io.github.kylinhunter.commons.generator.config.bean.Module;
import io.github.kylinhunter.commons.generator.config.bean.Template;
import io.github.kylinhunter.commons.generator.constant.ContextConsts;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 00:57
 **/
@Data
public class TemplateContext {
    private Module module;
    private Template template;
    private Map<String, Object> context = Maps.newHashMap();

    public TemplateContext(Module module, Template template) {
        this.module = module;
        this.template = template;
        this.context.putAll(template.getContext());
        this.context.putAll(module.getContext());
    }

    /**
     * @param contextData contextData
     * @return void
     * @title addAll
     * @description
     * @author BiJi'an
     * @date 2023-02-18 02:43
     */
    public void putContext(Map<String, ?> contextData) {
        this.context.putAll(contextData);
    }

    /**
     * @param key    key
     * @param object object
     * @return void
     * @title put
     * @description
     * @author BiJi'an
     * @date 2023-02-18 22:17
     */
    public void putContext(String key, Object object) {
        this.context.put(key, object);
    }

    /**
     * @return java.lang.String
     * @title getClassName
     * @description
     * @author BiJi'an
     * @date 2023-02-19 20:22
     */

    public String getClassName() {
        return (String) context.get(ContextConsts.CLASS_NAME);
    }

    /**
     * @return java.lang.String
     * @title getPackageName
     * @description
     * @author BiJi'an
     * @date 2023-02-19 20:23
     */
    public String getPackageName() {
        return (String) context.get(ContextConsts.PACKAGE_NAME);
    }


}
