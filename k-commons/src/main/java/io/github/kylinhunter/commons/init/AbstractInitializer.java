package io.github.kylinhunter.commons.init;

import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023/6/19
 **/
public abstract class AbstractInitializer implements Initializer {

  @Setter
  protected DebugOption debugOption;


}
