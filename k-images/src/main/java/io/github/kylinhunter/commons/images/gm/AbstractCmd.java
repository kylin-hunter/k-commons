package io.github.kylinhunter.commons.images.gm;

import io.github.kylinhunter.commons.component.simple.EnumServiceFactory;
import io.github.kylinhunter.commons.images.gm.arg.Args;
import io.github.kylinhunter.commons.images.gm.arg.CmdContext;
import java.util.List;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 00:08
 */
public class AbstractCmd {

  protected static EnumServiceFactory argFactory = new EnumServiceFactory();

  static {
    argFactory.init(Args.class);
  }

  protected CmdContext cmdContext = new CmdContext();

  public AbstractCmd() {

  }

  /**
   * @param cmd cmd
   * @title setCmd
   * @description setCmd  e.g.   gm convert
   * @author BiJi'an
   * @date 2024-01-05 14:15
   */
  public void setCmd(String... cmd) {
    cmdContext.setCmd(cmd);
  }

  /**
   * @return java.util.List<java.lang.String>
   * @title getCmdline
   * @description getCmdline e.g.   gm convert -resize 100x100 a.jgp
   * @author BiJi'an
   * @date 2024-01-05 14:17
   */

  public List<String> getCmdline() {

    return cmdContext.getCmdline();

  }

  public void addSourceImage(String source) {
//    cmdContext.addSourceImage(source);
  }
}