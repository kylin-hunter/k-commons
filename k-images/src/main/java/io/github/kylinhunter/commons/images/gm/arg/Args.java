package io.github.kylinhunter.commons.images.gm.arg;

import io.github.kylinhunter.commons.component.simple.EnumService;
import io.github.kylinhunter.commons.images.gm.arg.imp.ArgInt;
import io.github.kylinhunter.commons.images.gm.arg.imp.ArgSize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 00:22
 */
@RequiredArgsConstructor
public enum Args implements EnumService<Arg> {
  RESIZE(ArgSize.class, new Object[]{"resize"}),
  DEPTH(ArgInt.class, new Object[]{"depth"});
  @Getter
  private final Class<? extends Arg> srvClazz;
  @Getter
  private final Object[] initArgs;

  @SuppressWarnings("rawtypes")
  @Override
  public Class[] getInitArgTypes() {
    return new Class[]{String.class};
  }

}