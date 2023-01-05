package io.github.kylinhunter.commons.template.bean;

import java.io.File;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.github.kylinhunter.commons.template.config.GlobalConfig;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-06 00:50
 **/
@Data
public class TemplateOutputs {
    private final GlobalConfig globalConfig;
    List<TemplateOutput> datas = Lists.newArrayList();

    /**
     * @param name     name
     * @param encoding encoding
     * @param file
     * @return void
     * @title addTemplate
     * @description
     * @author BiJi'an
     * @date 2023-01-06 01:14
     */
    public void addTemplate(String name, String encoding, File file) {
        datas.add(new TemplateOutput(name, encoding, file));
    }


    public void addTemplate(String name, File file) {
        this.addTemplate(name, null, file);
    }
}
