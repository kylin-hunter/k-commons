package io.github.kylinhunter.commons.template.bean;

import java.nio.charset.Charset;
import java.nio.file.Path;

import lombok.Data;

/**
 * @author BiJi'an
 * @description the templateInfo output message
 * @date 2023-01-06 00:50
 **/
@Data
public class Output {
    private TemplateInfo templateInfo;
    private Charset encoding;
    private String extension;
    private Path outputPath;

    /**
     * @param templateInfo templateInfo
     * @return
     * @title Output
     * @description
     * @author BiJi'an
     * @date 2023-01-08 22:55
     */
    public Output(TemplateInfo templateInfo) {
        this.templateInfo = templateInfo;

    }

}
