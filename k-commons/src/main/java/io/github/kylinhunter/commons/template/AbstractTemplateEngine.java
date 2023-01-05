package io.github.kylinhunter.commons.template;

import io.github.kylinhunter.commons.template.config.GlobalConfig;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-05 21:56
 **/
@Data
public abstract class AbstractTemplateEngine implements TemplateEngine {
    protected GlobalConfig globalConfig = new GlobalConfig();

}
