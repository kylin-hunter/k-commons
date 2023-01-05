package io.github.kylinhunter.commons.template.velocity.builder;

import java.io.File;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.template.Engines;
import io.github.kylinhunter.commons.template.TemplateBuilder;
import io.github.kylinhunter.commons.template.TemplateEngine;

class VelocityTemplateBuilderTest {

    @Test
    void test() {
        TemplateEngine templateEngine = CF.get(Engines.VELOCITY);
        TemplateBuilder templateBuilder = templateEngine.createTemplateBuilder();
        File tmpFile = UserDirUtils.getTmpFile("test1.html");
        System.out.println(tmpFile.getAbsolutePath());
        templateBuilder.addTemplate("test", tmpFile);
        templateBuilder.putContext("now", LocalDateTime.now());
        templateBuilder.output((t) -> {
            System.out.println(t);

        });
    }

}