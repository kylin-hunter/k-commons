package io.github.kylinhunter.commons.images.gm;

import io.github.kylinhunter.commons.component.simple.EnumServiceFactory;
import io.github.kylinhunter.commons.images.gm.arg.ArgContext;
import io.github.kylinhunter.commons.images.gm.arg.Args;
import java.util.List;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 00:08
 */
public class AbstractCmd {

  private String[] cmd;
  protected static EnumServiceFactory factory = new EnumServiceFactory();

  static {
    factory.init(Args.class);
  }

  protected ArgContext argContext = new ArgContext();

  public AbstractCmd() {

  }

  public void setCmd(String... cmd) {
    this.cmd = cmd;
  }

  public List<String> getArgs() {

    List<String> args = argContext.getArgs();
    for (int i = cmd.length - 1; i >= 0; i--) {
      args.add(0, cmd[i]);
    }

    return args;


  }


}