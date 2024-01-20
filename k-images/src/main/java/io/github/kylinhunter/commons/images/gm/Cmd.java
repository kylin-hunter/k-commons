package io.github.kylinhunter.commons.images.gm;

import io.github.kylinhunter.commons.cmd.ExecResult;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 10:46
 */
public interface Cmd {

  ExecResult run();

  String command();

}