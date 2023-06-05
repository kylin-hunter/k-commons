package io.github.kylinhunter.commons.generator.config.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-23 00:01
 */
@Getter
@Setter
public class Config {
  private Global global;
  private Modules modules;
  private Templates templates;
}
