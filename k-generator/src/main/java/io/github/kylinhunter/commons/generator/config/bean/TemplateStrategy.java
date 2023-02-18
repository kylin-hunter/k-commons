package io.github.kylinhunter.commons.generator.config.bean;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TemplateStrategy extends GlobalStrategy {
    private String superClass;
    private String classNamePattern;

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

        if (StringUtils.isEmpty(this.packagePattern)) {
            this.packagePattern = globalStrategy.packagePattern;
        }

        if (StringUtils.isEmpty(this.encoding)) {
            this.encoding = globalStrategy.encoding;
        }

        if (StringUtils.isEmpty(this.extension)) {
            this.extension = globalStrategy.extension;
        }

    }

}
