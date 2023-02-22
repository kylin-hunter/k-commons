package io.github.kylinhunter.commons.template.config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.template.exception.TemplateException;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-06 01:40
 **/
@Data
public class TemplateConfig {

    private Path templateDir = UserDirUtils.getDir("templates", true).toPath();
    private Path outputDir = UserDirUtils.getDir("output", true).toPath();
    private boolean defaultOutputDirClean = false;
    private boolean defaultOutputDirAutoCreate = true;
    private boolean outputOverride = true;
    private Charset outputDefaultEncoding = StandardCharsets.UTF_8;

    /**
     * @param outputDir outputDir
     * @return void
     * @title setOutputDir
     * @description
     * @author BiJi'an
     * @date 2023-01-08 23:08
     */
    public void setOutputDir(Path outputDir) {
        if (Files.exists(outputDir)) {
            if (!Files.isDirectory(outputDir)) {
                throw new TemplateException("invalid outputDir" + outputDir);
            }
        } else {
            if (defaultOutputDirAutoCreate) {
                try {
                    Files.createDirectories(outputDir);
                } catch (IOException e) {
                    throw new TemplateException("create outputDir error" + outputDir, e);
                }
            } else {
                throw new TemplateException(" outputDir no exist " + outputDir);
            }
        }
        this.outputDir = outputDir;
    }

    /**
     * @param templateDir templateDir
     * @return void
     * @title setOutputDir
     * @description
     * @author BiJi'an
     * @date 2023-01-08 23:08
     */
    public void setTemplateDir(Path templateDir) {
        if (Files.exists(templateDir)) {
            if (!Files.isDirectory(templateDir)) {
                throw new TemplateException("invalid templateDir" + templateDir);
            }
        } else {

            throw new TemplateException(" templateDir no exist " + templateDir);

        }
        this.templateDir = templateDir;
    }

}
