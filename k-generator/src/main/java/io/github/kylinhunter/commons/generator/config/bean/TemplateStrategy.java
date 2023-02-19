package io.github.kylinhunter.commons.generator.config.bean;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.strings.CharsetConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TemplateStrategy extends Strategy {
    private String superClass;
    private String className;
    private String extension;

    /**
     * @param strategy globalStrategy
     * @return void
     * @title merge
     * @description
     * @author BiJi'an
     * @date 2023-02-18 21:02
     */
    public void merge(Strategy strategy) {
        if (strategy == null) {
            return;
        }

        if (StringUtils.isEmpty(this.packageName)) {
            this.packageName = strategy.packageName;
        }

        if (StringUtils.isEmpty(this.templateEncoding)) {
            this.templateEncoding = StringUtils.defaultIfBlank(strategy.templateEncoding, CharsetConst.UTF_8);
        }

        if (StringUtils.isEmpty(this.outputEncoding)) {
            this.outputEncoding = StringUtils.defaultIfBlank(strategy.outputEncoding, CharsetConst.UTF_8);
        }

    }

}
