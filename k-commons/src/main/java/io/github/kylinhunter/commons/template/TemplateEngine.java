package io.github.kylinhunter.commons.template;

import io.github.kylinhunter.commons.template.config.GlobalConfig;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-05 16:15
 **/
public interface TemplateEngine {

    TemplateBuilder createTemplateBuilder();

    GlobalConfig getGlobalConfig();
}
