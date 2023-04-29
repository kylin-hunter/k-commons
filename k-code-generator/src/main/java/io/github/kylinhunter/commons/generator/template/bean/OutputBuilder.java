package io.github.kylinhunter.commons.generator.template.bean;

import io.github.kylinhunter.commons.generator.template.TemplateExecutor;
import io.github.kylinhunter.commons.generator.template.config.TemplateConfig;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Path;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-06 00:50
 **/
@Data
public class OutputBuilder {
    private TemplateConfig templateConfig;
    private TemplateInfo templateInfo;
    private Output output;
    private TemplateExecutor templateExecutor;

    public OutputBuilder(TemplateExecutor templateExecutor, TemplateInfo templateInfo) {
        this.templateExecutor = templateExecutor;
        this.templateConfig = templateExecutor.getTemplateConfig();
        this.output = new Output(templateInfo);
    }

    /**
     * @param file file
     * @return io.github.kylinhunter.commons.templateInfo.bean.OutputBuilder
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
     * @return io.github.kylinhunter.commons.templateInfo.bean.OutputBuilder
     * @title outputRelativePath
     * @description
     * @author BiJi'an
     * @date 2023-01-08 22:56
     */
    public OutputBuilder outputRelativePath(String relativePath) {
        Path defaultOutputDir = templateConfig.getOutputConfig().getOutputPath();
        Path path = defaultOutputDir.resolve(relativePath);
        this.output.setOutputPath(path);
        return this;
    }

    /**
     * @param charset charset
     * @return io.github.kylinhunter.commons.template.bean.OutputBuilder
     * @title encoding
     * @description
     * @author BiJi'an
     * @date 2023-02-10 01:32
     */
    public OutputBuilder encoding(String charset) {
        if (charset != null) {
            this.output.setEncoding(Charset.forName(charset));
        }
        return this;
    }

    /**
     * @param extension extension
     * @return io.github.kylinhunter.commons.template.bean.OutputBuilder
     * @title setExtension
     * @description
     * @author BiJi'an
     * @date 2023-02-19 20:03
     */
    public OutputBuilder extension(String extension) {
        this.output.setExtension(extension);
        return this;
    }

    /**
     * @param charset charset
     * @return io.github.kylinhunter.commons.clazz.template.bean.OutputBuilder
     * @title encoding
     * @description
     * @author BiJi'an
     * @date 2023-01-08 01:03
     */
    public OutputBuilder encoding(Charset charset) {
        this.output.setEncoding(charset);
        return this;
    }

    /**
     * @return io.github.kylinhunter.commons.templateInfo.bean.Output
     * @title build
     * @description
     * @author BiJi'an
     * @date 2023-01-06 15:36
     */
    public void build() {
        this.templateExecutor.addOutput(this.output);
    }
}
