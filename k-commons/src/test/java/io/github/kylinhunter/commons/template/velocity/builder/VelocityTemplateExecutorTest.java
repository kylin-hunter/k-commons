package io.github.kylinhunter.commons.template.velocity.builder;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.template.Engines;
import io.github.kylinhunter.commons.template.TemplateExecutor;
import io.github.kylinhunter.commons.template.TemplateEngine;

class VelocityTemplateExecutorTest {

    @Test
    void testTemplateInClass() {
        String templateInClass = "template_in_class.vm";
        TemplateEngine templateEngine = CF.get(Engines.VELOCITY);
        TemplateExecutor templateExecutor = templateEngine.createTemplateExecutor();
        templateExecutor.putContext("now", LocalDateTime.now());

        templateExecutor.tmplate(templateInClass).outputRelativePath("output1_result1.html").build();
        File outpuFile = UserDirUtils.getTmpFile("template_output2/output2_result1.html");
        templateExecutor.tmplate(templateInClass).outputToFile(outpuFile).build();
        templateExecutor.tmplate(templateInClass).build();
        templateExecutor.output(System.out::println);

    }

    @Test
    void testTemplateInFile() {

        String templateInFile = "template_in_file";
        TemplateEngine templateEngine = CF.get(Engines.VELOCITY);
        TemplateExecutor templateExecutor = templateEngine.createTemplateExecutor();
        templateExecutor.putContext("now", LocalDateTime.now());

        //  template_in_file  and setDefaultOutputDir
        templateEngine.customize(config -> {
            File defaultDir = UserDirUtils.getTmpDir("template_output3");
            config.setDefaultOutputDir(defaultDir.toPath());
            config.setDefaultOutputDirClean(true);

        });
        templateExecutor.tmplate(templateInFile).outputRelativePath("output3_result1.html").encoding("UTF-8").build();
        templateExecutor.output(System.out::println);

    }

}