package io.github.kylinhunter.commons.generator.context.bean;

import java.util.Map;

import com.google.common.collect.Maps;

import io.github.kylinhunter.commons.generator.config.bean.Module;
import io.github.kylinhunter.commons.generator.config.bean.Template;
import io.github.kylinhunter.commons.generator.constant.ContextConsts;
import io.github.kylinhunter.commons.util.ObjectValues;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 00:57
 **/
@Data
public class TemplateContext {
    private final ModuleInfo moduleInfo;
    private final Module module;
    private final Template template;
    private ClassInfo classInfo = new ClassInfo();
    private Map<String, Object> context = Maps.newHashMap();

    /**
     * @param contextData contextData
     * @return void
     * @title addAll
     * @description
     * @author BiJi'an
     * @date 2023-02-18 02:43
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
     * @date 2023-02-18 22:17
     */
    public void putContext(String key, Object object) {
        this.context.put(key, object);
    }

    /**
     * @return boolean
     * @title isLombokEnabled
     * @description
     * @author BiJi'an
     * @date 2023-02-19 10:48
     */
    public boolean isLombokEnabled() {
        return ObjectValues.getBoolean(context.get(ContextConsts.LOMBOK_ENABLED));
    }

    /**
     * @return boolean
     * @title isWaggerEnabled
     * @description
     * @author BiJi'an
     * @date 2023-02-19 14:51
     */
    public boolean isWaggerEnabled() {
        return ObjectValues.getBoolean(context.get(ContextConsts.SWAGGER_ENABLED));
    }
    
}
