package io.github.kylinhunter.commons.generator.config.bean;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.strings.CharsetConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TemplateStrategy extends GlobalStrategy {
    private String superClass;
    private String className;

    /**
     * @param globalStrategy globalStrategy
     * @return void
     * @title merge
     * @description
     * @author BiJi'an
     * @date 2023-02-18 21:02
     */
    public void merge(GlobalStrategy globalStrategy) {
        if (globalStrategy == null) {
            return;
        }

        if (StringUtils.isEmpty(this.packageName)) {
            this.packageName = globalStrategy.packageName;
        }

        if (StringUtils.isEmpty(this.templateEncoding)) {
            this.templateEncoding = StringUtils.defaultIfBlank(globalStrategy.templateEncoding, CharsetConst.UTF_8);
        }

        if (StringUtils.isEmpty(this.outputEncoding)) {
            this.outputEncoding = StringUtils.defaultIfBlank(globalStrategy.outputEncoding, CharsetConst.UTF_8);
        }

    }

}
