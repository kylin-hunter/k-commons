package io.github.kylinhunter.commons.generator.template.config;

import io.github.kylinhunter.commons.generator.template.exception.TemplateException;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-06 01:40
 */
@Data
public class TemplateConfig {

  private Path templatePath = UserDirUtils.getDir("templates", true).toPath();
  private OutputConfig outputConfig = new OutputConfig();

  /**
   * @param templatePath templateDir
   * @return void
   * @title setOutputDir
   * @description
   * @author BiJi'an
   * @date 2023-01-08 23:08
   */
  public void setTemplatePath(Path templatePath) {
    if (Files.exists(templatePath)) {
      if (!Files.isDirectory(templatePath)) {
        throw new TemplateException("invalid templateDir" + templatePath);
      }
    } else {

      throw new TemplateException(" templateDir no exist " + templatePath);
    }
    this.templatePath = templatePath;
  }
}
