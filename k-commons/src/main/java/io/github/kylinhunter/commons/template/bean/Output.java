package io.github.kylinhunter.commons.template.bean;

import java.nio.file.Path;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.exception.check.ExceptionChecker;
import io.github.kylinhunter.commons.io.file.FileExtensions;
import io.github.kylinhunter.commons.util.FilenameUtils;
import lombok.Data;

/**
 * @author BiJi'an
 * @description the template output message
 * @date 2023-01-06 00:50
 **/
@Data
public class Output {
    private String name;
    private String encoding;
    private Path outputPath;

    /**
     * @param name name
     * @return
     * @title Output
     * @description
     * @author BiJi'an
     * @date 2023-01-08 22:55
     */
    public Output(String name) {
        this(name, null);
    }

    /**
     * @param name     name
     * @param encoding encoding
     * @return
     * @title Output
     * @description
     * @author BiJi'an
     * @date 2023-01-08 22:55
     */
    public Output(String name, String encoding) {
        ExceptionChecker.checkNotEmpty(name, "name not empty");
        String extension = FilenameUtils.getExtension(name);
        if (StringUtils.isEmpty(extension)) {
            name = name + FileExtensions.DOT_VM;
        }
        this.name = name;
        this.encoding = encoding;
    }

}
