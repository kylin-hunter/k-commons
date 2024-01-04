package io.github.kylinhunter.commons.images.gm.arg;

import io.github.kylinhunter.commons.exception.check.ThrowChecker;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 00:40
 */
@RequiredArgsConstructor
public abstract class AbstractArg implements Arg {

  protected final String argName;
  protected final int argLength;
  @Setter
  private ArgContext argContext;


  public void build(ArgContext argContext, Object... args) {
    ThrowChecker.checkArgument(args.length >= this.argLength, "arguments length");
    process(argContext, args);

  }

  public abstract void process(ArgContext argContext, Object... args);

}