package io.github.kylinhunter.commons.generator;

import java.io.File;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.template.Engines;
import io.github.kylinhunter.commons.template.TemplateEngine;
import io.github.kylinhunter.commons.template.TemplateExecutor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 22:10
 **/
public class CodeGenerator {
    @Test
    public void test() {
        String templateInFile = "entity.java.vm";
        TemplateEngine templateEngine = CF.get(Engines.VELOCITY);
        TemplateExecutor templateExecutor = templateEngine.createTemplateExecutor();
        templateExecutor.putContext("now", LocalDateTime.now());

        //  template_in_file  and setDefaultOutputDir
        templateEngine.customize(config -> {
            File defaultDir = UserDirUtils.getDir("test", true);
            config.setDefaultOutputDir(defaultDir.toPath());
            config.setDefaultOutputDirClean(true);

        });
        templateExecutor.tmplate(templateInFile).outputRelativePath("output3_result1.html").encoding("UTF-8").build();
        templateExecutor.output(System.out::println);
    }
}


