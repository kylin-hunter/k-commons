package io.github.kylinhunter.commons.template;

import io.github.kylinhunter.commons.template.config.ConfigCustomize;
import io.github.kylinhunter.commons.template.config.TemplateConfig;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-05 16:15
 **/
public interface TemplateEngine {

    TemplateExecutor createTemplateExecutor();

    void customize(ConfigCustomize<TemplateConfig> configCustomize);
}
