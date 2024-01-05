package io.github.kylinhunter.commons.images.gm.arg.imp;

import io.github.kylinhunter.commons.exception.embed.ParamException;
import io.github.kylinhunter.commons.images.gm.arg.AbstractArg;
import io.github.kylinhunter.commons.images.gm.arg.CmdContext;
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

  /**
   * @param cmdContext cmdContext
   * @param args       args
   * @title process
   * @description process size arg, e.g. -size 100x200
   * @author BiJi'an
   * @date 2024-01-05 10:33
   */

  @Override
  public void process(CmdContext cmdContext, Object... args) {

    int width = ObjectValues.getInt(args[0], 0);
    int height = ObjectValues.getInt(args[1], 0);

    if (width > 0) {
      if (height <= 0) {
        cmdContext.addArg(name, String.valueOf(width));
      } else {
        cmdContext.addArg(name, width + "x" + height);
      }
    } else {
      throw new ParamException("arg:" + name + ":the width must be greater than 0");
    }
  }
}