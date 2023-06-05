package io.github.kylinhunter.commons.generator.config.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-26 14:50
 */
@Setter
@Getter
public class Output {
  private String path;
  private boolean autoClean = false;
  private boolean autoCreate = true;
}
