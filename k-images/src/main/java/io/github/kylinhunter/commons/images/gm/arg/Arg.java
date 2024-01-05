package io.github.kylinhunter.commons.images.gm.arg;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 00:39
 */
public interface Arg {

  /**
   * @param cmdContext cmdContext
   * @param args       args
   * @title build
   * @description Abstract tool methods for building arguments, such as: -szie 100x100, -gravity
   * center
   * @author BiJi'an
   * @date 2024-01-05 10:45
   */
  void build(CmdContext cmdContext, Object... args);

}