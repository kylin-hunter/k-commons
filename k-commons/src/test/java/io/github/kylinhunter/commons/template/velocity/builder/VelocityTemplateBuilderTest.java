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
    void testTemplateInClass() {
        String templateInClass = "template_in_class";
        TemplateEngine templateEngine = CF.get(Engines.VELOCITY);
        TemplateBuilder templateBuilder = templateEngine.createTemplateBuilder();
        templateBuilder.putContext("now", LocalDateTime.now());

        templateBuilder.tmplate(templateInClass).outputRelativePath("output1_result1.html").build();
        File outpuFile = UserDirUtils.getTmpFile("template_output2/output2_result1.html");
        templateBuilder.tmplate(templateInClass).outputToFile(outpuFile).build();
        templateBuilder.tmplate(templateInClass).build();
        templateBuilder.output(System.out::println);

    }

    @Test
    void testTemplateInFile() {

        String templateInFile = "template_in_file";
        TemplateEngine templateEngine = CF.get(Engines.VELOCITY);
        TemplateBuilder templateBuilder = templateEngine.createTemplateBuilder();
        templateBuilder.putContext("now", LocalDateTime.now());

        //  template_in_file  and setDefaultOutputDir
        templateEngine.customize(config -> {
            File defaultDir = UserDirUtils.getTmpDir("template_output3");
            config.setDefaultOutputDir(defaultDir.toPath());
            config.setDefaultOutputDirClean(true);

        });
        templateBuilder.tmplate(templateInFile).outputRelativePath("output3_result1.html").build();
        templateBuilder.output(System.out::println);

    }

}