package io.github.kylinhunter.commons.template.velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.ToolContext;

import com.google.common.collect.Lists;

import io.github.kylinhunter.commons.io.file.FileExtensions;
import io.github.kylinhunter.commons.template.OutputProcessor;
import io.github.kylinhunter.commons.template.TemplateExecutor;
import io.github.kylinhunter.commons.template.bean.Output;
import io.github.kylinhunter.commons.template.bean.OutputBuilder;
import io.github.kylinhunter.commons.template.bean.TemplateInfo;
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
public class VelocityTemplateExecutor implements TemplateExecutor {

    private ToolContext toolContext;
    private VelocityTemplateEngine velocityTemplateEngine;
    private TemplateConfig templateConfig;
    private List<Output> outputs = Lists.newArrayList();

    public VelocityTemplateExecutor(VelocityTemplateEngine velocityTemplateEngine) {
        this.velocityTemplateEngine = velocityTemplateEngine;
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
     * @param name             name
     * @param encoding         encoding
     * @param defaultExtension defaultExtension
     * @return io.github.kylinhunter.commons.template.bean.OutputBuilder
     * @title tmplate
     * @description
     * @author BiJi'an
     * @date 2023-02-03 01:56
     */
    public OutputBuilder tmplate(String name, String encoding, String defaultExtension) {
        TemplateInfo templateInfo = new TemplateInfo(name, encoding, defaultExtension);
        return tmplate(templateInfo);
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
        TemplateInfo templateInfo = new TemplateInfo(name, encoding, FileExtensions.DOT_VM);
        return tmplate(templateInfo);
    }

    /**
     * @param name name
     * @return io.github.kylinhunter.commons.templateInfo.bean.OutputBuilder
     * @title tmplate
     * @description
     * @author BiJi'an
     * @date 2023-01-08 23:00
     */
    public OutputBuilder tmplate(String name) {
        TemplateInfo templateInfo = new TemplateInfo(name, null, FileExtensions.VM);
        return tmplate(templateInfo);
    }

    public OutputBuilder tmplate(TemplateInfo templateInfo) {
        return new OutputBuilder(this, templateInfo);

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
                        .requireNonNull(this.templateConfig.getOutputDir().toFile().listFiles())) {
                    log.info("delete file=>" + file.getAbsolutePath());
                    FileUtils.deleteQuietly(file);
                }
            }
            VelocityEngine velocityEngine = this.velocityTemplateEngine.getVelocityEngine();

            for (Output output : outputs) {
                final TemplateInfo templateInfo = output.getTemplateInfo();
                String templateName = templateInfo.getName();
                final String templateEncoding = templateInfo.getEncoding();

                Template template = velocityEngine.getTemplate(templateName, templateEncoding);

                StringWriter stringWriter = new StringWriter();
                template.merge(toolContext, stringWriter);
                String resultText = stringWriter.toString();
                Path outputPath = output.getOutputPath();
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
                                writeToFile(toFile, resultText, output.getEncoding());

                            } else {
                                log.warn(" skip outputOverride file=>" + toFile.getAbsolutePath());
                            }
                        }
                    } else {
                        writeToFile(toFile, resultText, output.getEncoding());
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
     * @date 2023-01-08 00:51
     */

    private void writeToFile(File file, String resultText, Charset encoding) throws IOException {
        log.info("output to file=> {}", file.getAbsolutePath());
        encoding = encoding == null ? templateConfig.getOutputDefaultEncoding() : encoding;
        FileUtils.writeStringToFile(file, resultText, encoding);
    }

}
