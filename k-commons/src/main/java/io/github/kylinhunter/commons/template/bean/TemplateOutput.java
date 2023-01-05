package io.github.kylinhunter.commons.template.bean;

import java.io.File;
import java.nio.file.Path;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.exception.check.ExceptionChecker;
import io.github.kylinhunter.commons.io.file.FileExtensions;
import io.github.kylinhunter.commons.util.FilenameUtils;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-06 00:50
 **/
@Data
public class TemplateOutput {
    private Path dir;
    private String name;
    private String encoding;
    private File file;
    private String relativePath;

    public TemplateOutput(String name,Path dir) {
        this(name, null, null, null);
    }

    public TemplateOutput(String name,Path dir, String encoding) {
        this(name, encoding, null, null);
    }

    public TemplateOutput(String name, Path dir,String encoding, String relativePath) {

        this(name, encoding, null, relativePath);

    }

    public TemplateOutput(String name, Path dir.String encoding, File file) {
        this(name, encoding, file, null);
    }

    public TemplateOutput(String name, String encoding, Path dir,File file, String relativePath) {
        ExceptionChecker.checkNotEmpty(name, "name not empty");
        String extension = FilenameUtils.getExtension(name);
        if (StringUtils.isEmpty(extension)) {
            name = name + FileExtensions.DOT_VM;
        }
        this.name = name;
        this.encoding = encoding;
        this.file = file;
        if (this.file == null) {

        }
        this.relativePath = relativePath;
    }
}
