/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.cmd;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.kylinhunter.commons.cmd.CmdResultReader.ResultType;
import io.github.kylinhunter.commons.exception.embed.GeneralException;
import java.io.Closeable;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-04 01:04
 */
public class CmdExecutor implements AutoCloseable, Closeable {

  private final ThreadPoolExecutor poolExecutor;

  public CmdExecutor() {
    this(Runtime.getRuntime().availableProcessors(),
        Runtime.getRuntime().availableProcessors() * 2, Integer.MAX_VALUE);
  }

  public CmdExecutor(int poolSize, int maxPoolSize, int capacity) {
    poolExecutor = new ThreadPoolExecutor(
        poolSize, maxPoolSize, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(capacity));
  }

  /**
   * @param cmds cmds
   * @return io.github.kylinhunter.commons.cmd.ExecResult
   * @title exec
   * @description exec
   * @author BiJi'an
   * @date 2024-01-10 01:03
   */
  public ExecResult exec(List<String> cmds) {
    return exec(cmds.toArray(new String[0]));
  }

  /**
   * @param cmds cmds
   * @return io.github.kylinhunter.commons.cmd.ExecResult
   * @title run
   * @description
   * @author BiJi'an
   * @date 2023-03-04 11:23
   */
  public ExecResult exec(String... cmds) {
    return exec(cmds, 60, TimeUnit.SECONDS);
  }

  /**
   * @param cmds    cmds
   * @param timeout timeout
   * @param unit    unit
   * @return io.github.kylinhunter.commons.cmd.ExecResult
   * @title exec
   * @description
   * @author BiJi'an
   * @date 2023-03-04 16:03
   */
  @SuppressFBWarnings("COMMAND_INJECTION")
  public ExecResult exec(String[] cmds, long timeout, TimeUnit unit) {
    try {
      ExecResult execResult = new ExecResult();
      ProcessBuilder builder = new ProcessBuilder(cmds);
      Process process = builder.start();

      Future<List<String>> stdOut =
          poolExecutor.submit(new CmdResultReader(process, ResultType.STD_OUT));
      Future<List<String>> stdErr =
          poolExecutor.submit(new CmdResultReader(process, ResultType.STD_ERR));
      boolean success = process.waitFor(timeout, unit);
      if (success) {
        execResult.setExitValue(process.exitValue());
      } else {
        execResult.setExitValue(Integer.MIN_VALUE);
      }

      execResult.setStdOuts(stdOut.get());
      execResult.setStdErrs(stdErr.get());
      return execResult;
    } catch (Exception e) {
      throw new GeneralException("exec err", e);
    }
  }

  @Override
  public void close() {
    this.poolExecutor.shutdownNow();
  }
}
