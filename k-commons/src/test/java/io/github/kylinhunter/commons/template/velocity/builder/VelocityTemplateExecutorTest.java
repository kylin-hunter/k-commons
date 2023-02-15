package io.github.kylinhunter.commons.template.velocity.builder;

import java.io.File;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.template.TemplateEngine;
import io.github.kylinhunter.commons.template.TemplateExecutor;
import io.github.kylinhunter.commons.template.velocity.VelocityTemplateEngine;

class VelocityTemplateExecutorTest {

    @Test
    void testTemplateInClass() {
        String templateInClass = "io/github/kylinhunter/commons/template/velocity/builder/template_in_class.vm";
        TemplateEngine templateEngine = new VelocityTemplateEngine();
        TemplateExecutor templateExecutor = templateEngine.createTemplateExecutor();
        templateExecutor.putContext("now", LocalDateTime.now());

        File outpuFile1 = UserDirUtils.getFile("output/output1_result1.html");
        if (outpuFile1.exists()) {
            outpuFile1.delete();
        }
        templateExecutor.tmplate(templateInClass).outputRelativePath("output1_result1.html").build();

        File outpuFile2 = UserDirUtils.getTmpFile("template_output2/output2_result1.html");
        if (outpuFile2.exists()) {
            outpuFile2.delete();
        }
        templateExecutor.tmplate(templateInClass).outputToFile(outpuFile2).build();

        templateExecutor.tmplate(templateInClass).build();
        templateExecutor.output(System.out::println);
        Assertions.assertTrue(outpuFile1.exists());
        Assertions.assertTrue(outpuFile2.exists());

    }

    @Test
    void testTemplateInFile() {

        String templateInFile = "template_in_file";
        TemplateEngine templateEngine = new VelocityTemplateEngine();
        TemplateExecutor templateExecutor = templateEngine.createTemplateExecutor();
        templateExecutor.putContext("now", LocalDateTime.now());

        //  template_in_file  and setOutputDir
        templateEngine.customize(config -> {
            File defaultDir = UserDirUtils.getTmpDir("template_output3");
            config.setOutputDir(defaultDir.toPath());
            config.setDefaultOutputDirClean(true);
            config.setTemplateDir(UserDirUtils.getDir("templates").toPath());

        });
        templateExecutor.tmplate(templateInFile).outputRelativePath("output3_result1.html").encoding("UTF-8").build();
        templateExecutor.output(System.out::println);

        File outpuFile3 = UserDirUtils.getTmpFile("template_output3/output3_result1.html");

        Assertions.assertTrue(outpuFile3.exists());

    }

}