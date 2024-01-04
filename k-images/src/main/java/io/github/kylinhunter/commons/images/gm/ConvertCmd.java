package io.github.kylinhunter.commons.images.gm;

import io.github.kylinhunter.commons.images.gm.arg.Args;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 00:05
 */
public class ConvertCmd extends AbstractCmd {

  public ConvertCmd() {
    this.setCmd("gm", "convert");
  }

  public void resize(int width, int height) {
    factory.get(Args.RESIZE).build(this.argContext, width, height);
  }

  public void depth(int depth) {
    factory.get(Args.DEPTH).build(this.argContext, depth);
  }
}