package io.github.kylinhunter.commons.generator.config.bean;

import io.github.kylinhunter.commons.strings.CharsetConst;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

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
   * @return void
   * @title merge
   * @description
   * @author BiJi'an
   * @date 2023-02-19 21:02
   */
  public void merge(Strategy strategy) {
    if (strategy == null) {
      return;
    }
    if (StringUtils.isEmpty(this.templateEncoding)) {
      this.templateEncoding =
          StringUtils.defaultIfBlank(strategy.templateEncoding, CharsetConst.UTF_8);
    }

    if (StringUtils.isEmpty(this.outputEncoding)) {
      this.outputEncoding = StringUtils.defaultIfBlank(strategy.outputEncoding, CharsetConst.UTF_8);
    }
  }
}
