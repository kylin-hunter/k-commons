package io.github.kylinhunter.commons.images.gm.arg.imp;

import io.github.kylinhunter.commons.exception.embed.ParamException;
import io.github.kylinhunter.commons.images.gm.arg.AbstractArg;
import io.github.kylinhunter.commons.images.gm.arg.CmdContext;
import io.github.kylinhunter.commons.util.ObjectValues;

/**
 * @author BiJi'an
 * @description the arg of int
 * @date 2024-01-01 00:40
 */
public class ArgInt extends AbstractArg {

  public ArgInt(String argName) {
    super(argName, 1);
  }

  /**
   * @param cmdContext cmdContext
   * @param args       args
   * @title process
   * @description process int  arg ，e.g.  -depth 8
   * @author BiJi'an
   * @date 2024-01-05 10:31
   */

  @Override
  public void process(CmdContext cmdContext, Object... args) {
    int width = ObjectValues.getInt(args[0], 0);
    if (width > 0) {
      cmdContext.addArg(name, String.valueOf(width));
    } else {
      throw new ParamException("arg:" + name + ":must be greater than 0");
    }
  }
}