package io.github.kylinhunter.commons.images.gm;

import io.github.kylinhunter.commons.images.gm.arg.Args;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 00:05
 */
public class GmConvert extends AbstractCmd {

  public GmConvert() {
    this.setCmd("gm", "convert");
  }

  /**
   * @param width  width
   * @param height height
   * @title resize
   * @description resize
   * @author BiJi'an
   * @date 2024-01-05 15:01
   */

  public void resize(int width, int height) {
    argFactory.get(Args.RESIZE).build(this.cmdContext, width, height);
  }

  /**
   * @param width  width
   * @param height height
   * @title size
   * @description size
   * @author BiJi'an
   * @date 2024-01-05 15:02
   */
  public void size(int width, int height) {
    argFactory.get(Args.SIZE).build(this.cmdContext, width, height);
  }

  /**
   * @param depth depth
   * @title depth
   * @description depth
   * @author BiJi'an
   * @date 2024-01-05 15:01
   */
  public void depth(int depth) {
    argFactory.get(Args.DEPTH).build(this.cmdContext, depth);
  }
}