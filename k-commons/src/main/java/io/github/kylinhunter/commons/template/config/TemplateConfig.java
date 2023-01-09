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

    private Path defaultOutputDir = UserDirUtils.getTmpDir("template_output", true).toPath();
    private boolean defaultOutputDirClean = false;
    private boolean defaultOutputDirAutoCreate = true;
    private boolean outputOverride = true;
    private Charset outputDefaultEncoding = StandardCharsets.UTF_8;

    /**
     * @param defaultOutputDir defaultOutputDir
     * @return void
     * @title setDefaultOutputDir
     * @description
     * @author BiJi'an
     * @date 2023-01-08 23:08
     */
    public void setDefaultOutputDir(Path defaultOutputDir) {
        if (Files.exists(defaultOutputDir)) {
            if (!Files.isDirectory(defaultOutputDir)) {
                throw new TemplateException("invalid defaultOutputDir" + defaultOutputDir);
            }
        } else {
            if (defaultOutputDirAutoCreate) {
                try {
                    Files.createDirectories(defaultOutputDir);
                } catch (IOException e) {
                    throw new TemplateException("create defaultOutputDir error" + defaultOutputDir, e);
                }
            } else {
                throw new TemplateException(" defaultOutputDir no exist " + defaultOutputDir);
            }
        }
        this.defaultOutputDir = defaultOutputDir;
    }

}
