package io.github.kylinhunter.commons.generator.template.exception;

import io.github.kylinhunter.commons.exception.embed.biz.BizException;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 23:47
 */
public class TemplateException extends BizException {
  public TemplateException(String message, Throwable cause) {
    super(message, cause);
  }

  public TemplateException(String message) {
    super(message);
  }
}
