package io.github.kylinhunter.commons.generator.config.bean;

import lombok.Data;

@Data
public class GlobalStrategy {
    protected String packageName;
    protected String templateEncoding;
    protected String outputEncoding;
}
