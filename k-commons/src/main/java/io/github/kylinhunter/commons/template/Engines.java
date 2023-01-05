package io.github.kylinhunter.commons.template;

import io.github.kylinhunter.commons.component.CT;
import io.github.kylinhunter.commons.template.velocity.VelocityTemplateEngine;
import lombok.Getter;

@Getter
public enum Engines implements CT<TemplateEngine> {
    VELOCITY(VelocityTemplateEngine.class);
    private Class<? extends TemplateEngine> clazz;

    Engines(Class<? extends TemplateEngine> clazz) {
        this.clazz = clazz;
    }
}
