package io.github.kylinhunter.commons.template.bean;

import java.io.File;
import java.nio.file.Path;

import io.github.kylinhunter.commons.template.TemplateBuilder;
import io.github.kylinhunter.commons.template.config.TemplateConfig;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-06 00:50
 **/
@Data
public class OutputBuilder {
    private TemplateConfig templateConfig;
    private Output output;
    private TemplateBuilder templateBuilder;

    public OutputBuilder(TemplateBuilder templateBuilder, String name,
                         String encoding) {
        this.templateBuilder = templateBuilder;
        this.templateConfig = templateBuilder.getTemplateConfig();
        this.output = new Output(name, encoding);
    }

    /**
     * @param file file
     * @return io.github.kylinhunter.commons.template.bean.OutputBuilder
     * @title output
     * @description
     * @author BiJi'an
     * @date 2023-01-08 22:56
     */

    public OutputBuilder outputToFile(File file) {
        this.output.setOutputPath(file.toPath());
        return this;
    }

    /**
     * @param relativePath relativePath
     * @return io.github.kylinhunter.commons.template.bean.OutputBuilder
     * @title outputRelativePath
     * @description
     * @author BiJi'an
     * @date 2023-01-08 22:56
     */
    public OutputBuilder outputRelativePath(String relativePath) {
        Path defaultOutputDir = templateConfig.getDefaultOutputDir();
        Path path = defaultOutputDir.resolve(relativePath);
        this.output.setOutputPath(path);
        return this;
    }

    /**
     * @return io.github.kylinhunter.commons.template.bean.Output
     * @title build
     * @description
     * @author BiJi'an
     * @date 2023-01-06 15:36
     */
    public void build() {
        this.templateBuilder.addOutput(this.output);
    }
}
