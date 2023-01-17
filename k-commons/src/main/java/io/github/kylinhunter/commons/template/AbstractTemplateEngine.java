package io.github.kylinhunter.commons.template;

import io.github.kylinhunter.commons.template.config.ConfigCustomize;
import io.github.kylinhunter.commons.template.config.TemplateConfig;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-05 21:56
 **/
@Data
public abstract class AbstractTemplateEngine implements TemplateEngine {
    protected TemplateConfig templateConfig = new TemplateConfig();

    /**
     * @see TemplateEngine#customize
     */
    @Override
    public void customize(ConfigCustomize<TemplateConfig> configCustomize) {
        configCustomize.customize(this.templateConfig);
    }

}
