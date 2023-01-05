package io.github.kylinhunter.commons.template.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import io.github.kylinhunter.commons.exception.embed.InternalException;
import io.github.kylinhunter.commons.exception.embed.KIOException;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-06 01:40
 **/
@Data
public class GlobalConfig {
    private boolean clearBeforExec = false;

    private Path defaultOutputDir = UserDirUtils.getTmpDir("auto_code", true).toPath();

    private boolean autoCreateOutputDir = false;

    private boolean fileOverride = false;

    public void setDefaultOutputDir(Path defaultOutputDir) {
        if (Files.exists(defaultOutputDir)) {
            if (!Files.isDirectory(defaultOutputDir)) {
                throw new InternalException("invalid defaultOutputDir" + defaultOutputDir);
            }
        } else {
            if (autoCreateOutputDir) {
                try {
                    Files.createDirectories(defaultOutputDir);
                } catch (IOException e) {
                    throw new KIOException("create defaultOutputDir error" + defaultOutputDir, e);
                }
            } else {
                throw new KIOException(" defaultOutputDir no exist " + defaultOutputDir);
            }
        }
        this.defaultOutputDir = defaultOutputDir;
    }

}
