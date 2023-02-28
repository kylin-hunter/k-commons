package io.github.kylinhunter.commons.template.config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.template.exception.TemplateException;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-06 01:40
 **/
@Data
public class OutputConfig {

    private Path outputPath = UserDirUtils.getDir("output", true).toPath();
    private boolean autoClean = false;
    private boolean autoCreate = true;
    private boolean override = true;
    private Charset defaultEncoding = StandardCharsets.UTF_8;

    /**
     * @param outputPath outputDir
     * @return void
     * @title setOutputDir
     * @description
     * @author BiJi'an
     * @date 2023-01-08 23:08
     */
    public void setOutputPath(Path outputPath) {
        if (Files.exists(outputPath)) {
            if (!Files.isDirectory(outputPath)) {
                throw new TemplateException("invalid outputPath " + outputPath);
            }
        } else {
            if (autoCreate) {
                try {
                    Files.createDirectories(outputPath);
                } catch (IOException e) {
                    throw new TemplateException("create outputPath error " + outputPath, e);
                }
            }
            if (!Files.exists(outputPath)) {
                throw new TemplateException(" outputPath no exist " + outputPath);
            }

        }
        this.outputPath = outputPath;
    }

    /**
     * @param outputPath outputPath
     * @return void
     * @title setOutputPath
     * @description
     * @author BiJi'an
     * @date 2023-02-26 15:20
     */
    public void setOutputPath(String outputPath) {
        Path path = ResourceHelper.getPath(outputPath, ResourceHelper.PathType.FILESYSTEM);
        this.setOutputPath(path);

    }

}