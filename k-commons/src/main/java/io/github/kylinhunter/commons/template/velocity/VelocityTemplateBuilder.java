package io.github.kylinhunter.commons.template.velocity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.ToolContext;

import io.github.kylinhunter.commons.template.OutputProcessor;
import io.github.kylinhunter.commons.template.TemplateBuilder;
import io.github.kylinhunter.commons.template.bean.Output;
import io.github.kylinhunter.commons.template.bean.OutputBuilder;
import io.github.kylinhunter.commons.template.config.TemplateConfig;
import io.github.kylinhunter.commons.template.exception.TemplateException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-05 22:01
 **/
@Data
@Slf4j
public class VelocityTemplateBuilder implements TemplateBuilder {

    private VelocityEngine velocityEngine;
    private ToolContext toolContext;
    private VelocityTemplateEngine velocityTemplateEngine;
    private TemplateConfig templateConfig;
    private List<Output> outputs = Lists.newArrayList();

    public VelocityTemplateBuilder(VelocityTemplateEngine velocityTemplateEngine) {
        this.velocityTemplateEngine = velocityTemplateEngine;
        this.velocityEngine = this.velocityTemplateEngine.getVelocityEngine();
        this.toolContext = this.velocityTemplateEngine.getToolManager().createContext();
        this.templateConfig = velocityTemplateEngine.getTemplateConfig();

    }

    /**
     * @param key   key
     * @param value value
     * @title put
     * @description
     * @author BiJi'an
     * @date 2023-01-05 22:02
     */
    @Override
    public void putContext(String key, Object value) {
        this.toolContext.put(key, value);
    }

    /**
     * @param name     name
     * @param encoding encoding
     * @return void
     * @title addTemplate
     * @description
     * @author BiJi'an
     * @date 2023-01-08 22:56
     */
    public OutputBuilder tmplate(String name, String encoding) {
        return new OutputBuilder(this, name, encoding);
    }

    /**
     * @param name name
     * @return io.github.kylinhunter.commons.template.bean.OutputBuilder
     * @title tmplate
     * @description
     * @author BiJi'an
     * @date 2023-01-08 23:00
     */
    public OutputBuilder tmplate(String name) {
        return tmplate(name, null);
    }

    @Override
    public void addOutput(Output output) {
        if (output != null) {
            this.outputs.add(output);
        }
    }

    @Override
    public void output() {
        this.output(null);
    }

    /**
     * @return void
     * @title output
     * @description
     * @author BiJi'an
     * @date 2023-01-05 22:15
     */
    @Override
    public void output(OutputProcessor outputProcessor) {
        try {
            if (templateConfig.isDefaultOutputDirClean()) {
                for (File file : Objects
                        .requireNonNull(this.templateConfig.getDefaultOutputDir().toFile().listFiles())) {
                    log.info("delete file=>" + file.getAbsolutePath());
                    FileUtils.deleteQuietly(file);
                }
            }

            for (Output output : outputs) {
                String name = output.getName();
                String encoding = output.getEncoding();
                Path outputPath = output.getOutputPath();

                Template template = !StringUtils.isEmpty(encoding) ?
                        this.velocityEngine.getTemplate(name, encoding) : this.velocityEngine.getTemplate(name);

                StringWriter stringWriter = new StringWriter();
                template.merge(toolContext, stringWriter);
                String resultText = stringWriter.toString();

                if (outputPath != null) {
                    File toFile = outputPath.toFile();
                    if (!toFile.getParentFile().exists()) {
                        throw new TemplateException("dir not exist:" + toFile.getParent());
                    }
                    if (toFile.exists()) {
                        if (!toFile.isFile()) {
                            throw new TemplateException("not a file" + toFile.getAbsolutePath());
                        } else {
                            if (templateConfig.isOutputOverride()) {
                                log.warn(" override file=>" + toFile.getAbsolutePath());
                                writeToFile(toFile, resultText);

                            } else {
                                log.warn(" skip outputOverride file=>" + toFile.getAbsolutePath());
                            }
                        }
                    } else {
                        writeToFile(toFile, resultText);
                    }

                }

                if (outputProcessor != null) {
                    outputProcessor.process(resultText);
                }

            }

        } catch (Exception e) {
            throw new TemplateException("output error", e);
        }
    }

    /**
     * @param file       file
     * @param resultText resultText
     * @return void
     * @title writeToFile
     * @description
     * @author BiJi'an
     * @date 2023-01-09 00:51
     */

    private void writeToFile(File file, String resultText) throws IOException {
        log.info("output to file=> {}", file.getAbsolutePath());
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(resultText);
        }
    }

}
