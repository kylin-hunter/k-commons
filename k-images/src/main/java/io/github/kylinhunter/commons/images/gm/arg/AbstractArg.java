package io.github.kylinhunter.commons.images.gm.arg;

import io.github.kylinhunter.commons.exception.check.ThrowChecker;
import io.github.kylinhunter.commons.images.gm.arg.constant.ArgPrefix;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 00:40
 */
@RequiredArgsConstructor
public abstract class AbstractArg implements Arg {

  protected final String name;
  protected final ArgPrefix argPrefix;
  protected final int minLen;

  /**
   * @param cmdContext cmdContext
   * @param args       args
   * @title build the arg
   * @description build
   * @author BiJi'an
   * @date 2024-01-05 10:16
   */

  public void build(CmdContext cmdContext, Object... args) {
    ThrowChecker.checkArgument(args.length >= this.minLen, "arguments length ");
    process(cmdContext, args);
  }

  /**
   * @param cmdContext cmdContext
   * @param args       args
   * @title the arg processing detail
   * @description process
   * @author BiJi'an
   * @date 2024-01-05 10:17
   */

  public abstract void process(CmdContext cmdContext, Object... args);

}