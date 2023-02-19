package io.github.kylinhunter.commons.generator.config.bean;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.strings.CharsetConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TemplateStrategy extends CommonStrategy {
    private String superClass;
    private String className;

    /**
     * @param commonStrategy globalStrategy
     * @return void
     * @title merge
     * @description
     * @author BiJi'an
     * @date 2023-02-18 21:02
     */
    public void merge(CommonStrategy commonStrategy) {
        if (commonStrategy == null) {
            return;
        }

        if (StringUtils.isEmpty(this.packageName)) {
            this.packageName = commonStrategy.packageName;
        }

        if (StringUtils.isEmpty(this.templateEncoding)) {
            this.templateEncoding = StringUtils.defaultIfBlank(commonStrategy.templateEncoding, CharsetConst.UTF_8);
        }

        if (StringUtils.isEmpty(this.outputEncoding)) {
            this.outputEncoding = StringUtils.defaultIfBlank(commonStrategy.outputEncoding, CharsetConst.UTF_8);
        }

    }

}
