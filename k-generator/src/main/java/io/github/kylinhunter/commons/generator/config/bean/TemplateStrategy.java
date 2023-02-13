package io.github.kylinhunter.commons.generator.config.bean;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

@Data
public class TemplateStrategy extends CommonStrategy {
    private String superClass;
    private String classNamePattern;

    public void merge(CommonStrategy commonStrategy) {
        if (commonStrategy == null) {
            return;
        }

        if (StringUtils.isEmpty(this.packagePattern)) {
            this.packagePattern = commonStrategy.packagePattern;
        }
        this.variables.putAll(commonStrategy.variables);

        if (!serializable) {
            this.serializable = commonStrategy.serializable;
        }

        if (!lombok) {
            this.lombok = commonStrategy.lombok;
        }

        if (!lombokChainModel) {
            this.lombokChainModel = commonStrategy.lombokChainModel;
        }

        if (StringUtils.isEmpty(this.encoding)) {
            this.encoding = commonStrategy.encoding;
        }

        if (StringUtils.isEmpty(this.extension)) {
            this.extension = commonStrategy.extension;
        }

    }

}
