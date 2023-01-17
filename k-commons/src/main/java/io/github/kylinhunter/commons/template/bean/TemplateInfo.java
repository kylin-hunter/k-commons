package io.github.kylinhunter.commons.template.bean;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.exception.check.ExceptionChecker;
import io.github.kylinhunter.commons.util.FilenameUtils;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 22:22
 **/
@Data
public class TemplateInfo {
    private String name;
    private String encoding;

    public TemplateInfo(String name, String encoding, String defaultExtension) {
        ExceptionChecker.checkNotEmpty(name, "name not empty");
        String extension = FilenameUtils.getExtension(name);
        if (StringUtils.isEmpty(extension)&&!StringUtils.isEmpty(defaultExtension)) {
            name = name + defaultExtension;
        }
        this.name = name;
        this.encoding = encoding;
    }

}
