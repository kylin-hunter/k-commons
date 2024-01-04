package io.github.kylinhunter.commons.images.gm.arg.imp;

import io.github.kylinhunter.commons.images.gm.arg.AbstractArg;
import io.github.kylinhunter.commons.images.gm.arg.ArgContext;
import io.github.kylinhunter.commons.util.ObjectValues;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 00:40
 */
public class ArgSize extends AbstractArg {


  public ArgSize(String argName) {
    super(argName, 1);
  }

  @Override
  public void process(ArgContext argContext, Object... args) {

    int width = ObjectValues.getInt(args[0], 0);
    int height = ObjectValues.getInt(args[1], 0);

    StringBuilder stringBuilder = new StringBuilder();
    argContext.add("-" + argName);

    if (width > 0) {
      stringBuilder.append(width);
    }
    if (width > 0 && height > 0) {
      stringBuilder.append("x");
    }
    if (height > 0) {
      stringBuilder.append(height);
    }
    if (stringBuilder.length() > 0) {
      argContext.add(stringBuilder.toString());
    }
  }
}