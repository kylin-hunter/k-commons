package io.github.kylinhunter.commons.images.gm.arg.imp;

import io.github.kylinhunter.commons.images.gm.arg.AbstractArg;
import io.github.kylinhunter.commons.images.gm.arg.ArgContext;
import io.github.kylinhunter.commons.util.ObjectValues;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 00:40
 */
public class ArgInt extends AbstractArg {

  public ArgInt(String argName) {
    super(argName, 1);
  }

  @Override
  public void process(ArgContext argContext, Object... args) {
    int width = ObjectValues.getInt(args[0], 0);

    StringBuilder stringBuilder = new StringBuilder();
    argContext.add("-" + argName);

    if (width > 0) {
      stringBuilder.append(width);
    }
    if (stringBuilder.length() > 0) {
      argContext.add(stringBuilder.toString());
    }
  }
}