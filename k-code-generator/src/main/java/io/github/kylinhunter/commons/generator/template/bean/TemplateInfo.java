package io.github.kylinhunter.commons.generator.template.bean;

import io.github.kylinhunter.commons.exception.check.ExceptionChecker;
import io.github.kylinhunter.commons.io.file.FilenameUtils;
import io.github.kylinhunter.commons.lang.strings.StringConst;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 22:22
 */
@Getter
@Setter
public class TemplateInfo {
  private String name;
  private String encoding;

  public TemplateInfo(String name, String encoding, String defaultExtension) {
    ExceptionChecker.checkNotEmpty(name, "name not empty");
    String extension = FilenameUtils.getExtension(name);
    if (!StringUtil.isEmpty(defaultExtension) && !defaultExtension.equalsIgnoreCase(extension)) {
      name = name + StringConst.DOT + defaultExtension;
    }
    this.name = name;
    this.encoding = encoding;
  }
}
