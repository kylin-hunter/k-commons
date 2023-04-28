package io.github.kylinhunter.commons.generator.template;

import java.io.File;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.generator.template.config.OutputConfig;
import io.github.kylinhunter.commons.generator.template.velocity.VelocityTemplateEngine;
import io.github.kylinhunter.commons.io.file.FileExtensions;
import io.github.kylinhunter.commons.io.file.FileUtil;
import io.github.kylinhunter.commons.io.file.UserDirUtils;

class VelocityTemplateExecutorTest {

    @Test
    void testTemplateInClass() {
        String templateInClass = "io/github/kylinhunter/commons/generator/template/template_in_class.vm";
        TemplateEngine templateEngine = new VelocityTemplateEngine();
        TemplateExecutor templateExecutor = templateEngine.createTemplateExecutor();
        templateExecutor.putContext("now", LocalDateTime.now());

        File outpuFile1 = UserDirUtils.getFile("output/output1_result1.html");
        if (outpuFile1.exists()) {
            FileUtil.delete(outpuFile1);
        }
        templateExecutor.tmplate(templateInClass).outputRelativePath("output1_result1.html").build();

        File outpuFile2 = UserDirUtils.getTmpFile("template_output2/output2_result1.html");
        if (outpuFile2.exists()) {
            FileUtil.delete(outpuFile2);
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
            OutputConfig outputConfig = config.getOutputConfig();
            outputConfig.setOutputPath(defaultDir.toPath());
            outputConfig.setAutoClean(true);
            config.setTemplatePath(UserDirUtils.getDir("templates").toPath());

        });
        templateExecutor.tmplate(templateInFile, null, FileExtensions.VM).outputRelativePath("output3_result1.html")
                .encoding("UTF-8").build();
        templateExecutor.output(System.out::println);

        File outpuFile3 = UserDirUtils.getTmpFile("template_output3/output3_result1.html");

        Assertions.assertTrue(outpuFile3.exists());

    }

}