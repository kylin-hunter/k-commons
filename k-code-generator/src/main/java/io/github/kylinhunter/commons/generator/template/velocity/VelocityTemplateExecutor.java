package io.github.kylinhunter.commons.generator.template.velocity;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.exception.embed.KIOException;
import io.github.kylinhunter.commons.generator.template.OutputProcessor;
import io.github.kylinhunter.commons.generator.template.TemplateExecutor;
import io.github.kylinhunter.commons.generator.template.bean.Output;
import io.github.kylinhunter.commons.generator.template.bean.OutputBuilder;
import io.github.kylinhunter.commons.generator.template.bean.TemplateInfo;
import io.github.kylinhunter.commons.generator.template.config.OutputConfig;
import io.github.kylinhunter.commons.generator.template.config.TemplateConfig;
import io.github.kylinhunter.commons.generator.template.exception.TemplateException;
import io.github.kylinhunter.commons.io.file.FileUtil;
import io.github.kylinhunter.commons.io.file.path.PathUtil;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.io.File;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.ToolContext;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-05 22:01
 */
@Data
@Slf4j
public class VelocityTemplateExecutor implements TemplateExecutor {

  private ToolContext toolContext;
  private VelocityTemplateEngine velocityTemplateEngine;
  private TemplateConfig templateConfig;
  private List<Output> outputs = ListUtils.newArrayList();

  public VelocityTemplateExecutor(VelocityTemplateEngine velocityTemplateEngine) {
    this(velocityTemplateEngine, null);
  }

  public VelocityTemplateExecutor(
      VelocityTemplateEngine velocityTemplateEngine, Map<String, Object> context) {
    this.velocityTemplateEngine = velocityTemplateEngine;
    this.toolContext = this.velocityTemplateEngine.getToolManager().createContext();
    this.templateConfig = velocityTemplateEngine.getTemplateConfig();
    if (context != null) {
      this.toolContext.putAll(context);
    }
  }

  /**
   * @param key key
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

  @Override
  public void putContext(Map<String, Object> values) {
    this.toolContext.putAll(values);
  }

  /**
   * @param name name
   * @param encoding encoding
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
   * @param name name
   * @param encoding encoding
   * @return void
   * @title addTemplate
   * @description
   * @author BiJi'an
   * @date 2023-01-08 22:56
   */
  public OutputBuilder tmplate(String name, String encoding) {
    TemplateInfo templateInfo = new TemplateInfo(name, encoding, null);
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
    TemplateInfo templateInfo = new TemplateInfo(name, null, null);
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

    OutputConfig outputConfig = templateConfig.getOutputConfig();

    VelocityEngine velocityEngine = this.velocityTemplateEngine.getVelocityEngine();

    for (Output output : outputs) {
      final TemplateInfo templateInfo = output.getTemplateInfo();
      String templateName = templateInfo.getName();
      final String templateEncoding = templateInfo.getEncoding();
      Template template;
      if (!StringUtil.isEmpty(templateEncoding)) {
        template = velocityEngine.getTemplate(templateName, templateEncoding);
      } else {
        template = velocityEngine.getTemplate(templateName);
      }
      StringWriter stringWriter = new StringWriter();
      template.merge(toolContext, stringWriter);
      String resultText = stringWriter.toString();
      Path outputPath = output.getOutputPath();
      if (outputPath != null) {
        String extension = output.getExtension();
        if (!StringUtil.isEmpty(extension)) {
          outputPath = PathUtil.get(outputPath.toFile().getAbsolutePath() + "." + extension);
        }
        File toFile = outputPath.toFile();
        if (!toFile.getParentFile().exists()) {
          FileUtil.forceMkdir(toFile.getParentFile());
        }
        if (toFile.exists()) {
          if (!toFile.isFile()) {
            throw new TemplateException("not a file" + toFile.getAbsolutePath());
          } else {
            if (outputConfig.isOverride()) {
              log.warn("override file=>" + toFile.getAbsolutePath());
              writeToFile(toFile, resultText, output.getEncoding());

            } else {
              log.warn("skip outputOverride file=>" + toFile.getAbsolutePath());
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
  }

  /**
   * @param file file
   * @param resultText resultText
   * @return void
   * @title writeToFile
   * @description
   * @author BiJi'an
   * @date 2023-01-08 00:51
   */
  private void writeToFile(File file, String resultText, Charset encoding) {
    try {
      log.info("output to file=> {}", file.getAbsolutePath());
      OutputConfig outputConfig = templateConfig.getOutputConfig();
      encoding = encoding == null ? outputConfig.getDefaultEncoding() : encoding;
      FileUtils.writeStringToFile(file, resultText, encoding);
    } catch (Exception e) {
      throw new KIOException("write  to file error", e);
    }
  }
}
