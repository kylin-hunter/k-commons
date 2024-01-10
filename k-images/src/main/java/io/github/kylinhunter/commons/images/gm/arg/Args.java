package io.github.kylinhunter.commons.images.gm.arg;

import io.github.kylinhunter.commons.component.simple.EnumService;
import io.github.kylinhunter.commons.images.gm.arg.constant.ArgPrefix;
import io.github.kylinhunter.commons.images.gm.arg.imp.ArgInt;
import io.github.kylinhunter.commons.images.gm.arg.imp.ArgSize;
import io.github.kylinhunter.commons.images.gm.arg.imp.ArgString;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 00:22
 */
@RequiredArgsConstructor
public enum Args implements EnumService<Arg> {
  RESIZE(ArgSize.class, new Object[]{"resize", ArgPrefix.MINUS}),
  SIZE(ArgSize.class, new Object[]{"size", ArgPrefix.MINUS}),
  DEPTH(ArgInt.class, new Object[]{"depth", ArgPrefix.MINUS}),
  REMOVE_PROFILE(ArgString.class, new Object[]{"profile", ArgPrefix.PLUS}),
  ADD_PROFILE(ArgString.class, new Object[]{"profile", ArgPrefix.MINUS});

  @Getter
  private final Class<? extends Arg> srvClazz;
  @Getter
  private final Object[] initArgs;

  @SuppressWarnings("rawtypes")
  @Override
  public Class[] getInitArgTypes() {
    return new Class[]{String.class, ArgPrefix.class};
  }

}