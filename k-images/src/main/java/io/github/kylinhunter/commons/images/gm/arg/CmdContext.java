package io.github.kylinhunter.commons.images.gm.arg;

import io.github.kylinhunter.commons.collections.ListUtils;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 00:08
 */
public class CmdContext {

  @Getter
  private List<String> cmds = ListUtils.newArrayList();


  /**
   * @param args args
   * @title addArg
   * @description addArg
   * @author BiJi'an
   * @date 2024-01-01 15:27
   */

  public void addArg(String... args) {
    if (args.length > 0) {
      if (args.length == 1) {
        cmds.add(args[0]);
      } else {
        Collections.addAll(cmds, args);
      }
    }
  }


  /**
   * @param cmds cmds
   * @title setCmd
   * @description setCmd
   * @author BiJi'an
   * @date 2024-01-01 14:01
   */

  public void setCmd(String... cmds) {
    for (int i = 0; i < cmds.length; i++) {
      this.cmds.add(i, cmds[i]);
    }
  }

  @Override
  public String toString() {
    StringJoiner stringJoiner = new StringJoiner(" ");
    for (String s : cmds) {
      stringJoiner.add(s);
    }
    return stringJoiner.toString();
  }

}


