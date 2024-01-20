package io.github.kylinhunter.commons.images.gm.arg.imp;

import io.github.kylinhunter.commons.exception.embed.ParamException;
import io.github.kylinhunter.commons.images.gm.arg.AbstractArg;
import io.github.kylinhunter.commons.images.gm.arg.CmdContext;
import io.github.kylinhunter.commons.images.gm.arg.constant.ArgPrefix;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import io.github.kylinhunter.commons.util.ObjectValues;

/**
 * @author BiJi'an
 * @description the arg of int
 * @date 2024-01-01 00:40
 */
public class ArgString extends AbstractArg {

  public ArgString(String argName, ArgPrefix argPrefix) {
    super(argName, argPrefix, 1);
  }

  /**
   * @param cmdContext cmdContext
   * @param args       args
   * @title process
   * @description process int  arg ，e.g.  -depth 8
   * @author BiJi'an
   * @date 2024-01-01 10:31
   */

  @Override
  public void process(CmdContext cmdContext, Object... args) {
    String arg0 = ObjectValues.getString(args[0]);
    if (!StringUtil.isEmpty(arg0)) {
      cmdContext.addArg(argPrefix.getPrefix() + name, arg0);
    } else {
      throw new ParamException("arg:" + name + ":must be greater than 0");
    }
  }
}