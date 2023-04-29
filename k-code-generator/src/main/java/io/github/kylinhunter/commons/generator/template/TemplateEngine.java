package io.github.kylinhunter.commons.generator.template;

import io.github.kylinhunter.commons.generator.template.config.ConfigCustomize;
import io.github.kylinhunter.commons.generator.template.config.TemplateConfig;
import java.util.Map;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-05 16:15
 */
public interface TemplateEngine {

  TemplateExecutor createTemplateExecutor();

  TemplateExecutor createTemplateExecutor(Map<String, Object> context);

  void customize(ConfigCustomize<TemplateConfig> configCustomize);
}
