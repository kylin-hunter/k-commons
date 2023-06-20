package io.github.kylinhunter.commons.init;

import io.github.kylinhunter.commons.exception.embed.InitException;

/**
 * @author BiJi'an
 * @description
 * @date 2023/6/19
 **/
public interface Initializer {

  void initialize() throws InitException;

  void setDebugOption(DebugOption debugOption);


}
