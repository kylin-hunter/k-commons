package io.github.kylinhunter.commons.generator.config.bean;

import io.github.kylinhunter.commons.io.Charsets;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class TemplateStrategy extends Strategy {
  protected String packageName;
  private String superClass;
  private String className;
  private String extension;
  private List<String> interfaces;

  /**
   * @param strategy globalStrategy
   * @title merge
   * @description
   * @author BiJi'an
   * @date 2023-02-19 21:02
   */
  public void merge(Strategy strategy) {
    if (strategy == null) {
      return;
    }
    if (StringUtil.isEmpty(this.templateEncoding)) {
      this.templateEncoding =
          StringUtil.defaultIfBlank(strategy.templateEncoding, Charsets.UTF_8_VALUE);
    }

    if (StringUtil.isEmpty(this.outputEncoding)) {
      this.outputEncoding =
          StringUtil.defaultIfBlank(strategy.outputEncoding, Charsets.UTF_8_VALUE);
    }
  }
}
