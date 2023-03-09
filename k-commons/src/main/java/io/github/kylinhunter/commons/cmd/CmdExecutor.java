package io.github.kylinhunter.commons.cmd;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.compress.utils.Lists;

import io.github.kylinhunter.commons.cmd.CmdResultReader.ResultType;
import io.github.kylinhunter.commons.exception.embed.GeneralException;
import io.github.kylinhunter.commons.juc.ThreadPoolExecutorFactory;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-04 01:04
 **/
public class CmdExecutor {

    private ThreadPoolExecutor poolExecutor = ThreadPoolExecutorFactory
            .register(CmdExecutor.class.getSimpleName(), 5, 10, 10);

    public void setPoolExecutor(ThreadPoolExecutor poolExecutor) {
        if (poolExecutor != null) {
            this.poolExecutor.shutdownNow();
            this.poolExecutor = poolExecutor;
        }

    }

    /**
     * @param cmds cmds
     * @return java.util.List<io.github.kylinhunter.commons.cmd.CmdResult>
     * @title exec
     * @description
     * @author BiJi'an
     * @date 2023-03-04 16:03
     */

    public List<CmdResult> exec(String... cmds) {
        List<CmdResult> cmdResults = Lists.newArrayList();
        for (String cmd : cmds) {
            cmdResults.add(exec(cmd));
        }
        return cmdResults;
    }

    /**
     * @param cmd cmd
     * @return io.github.kylinhunter.commons.cmd.CmdResult
     * @title run
     * @description
     * @author BiJi'an
     * @date 2023-03-04 11:23
     */
    public CmdResult exec(String cmd) {
        return exec(cmd, 60, TimeUnit.SECONDS);

    }

    /**
     * @param cmd     cmd
     * @param timeout timeout
     * @param unit    unit
     * @return io.github.kylinhunter.commons.cmd.CmdResult
     * @title exec
     * @description
     * @author BiJi'an
     * @date 2023-03-04 16:03
     */
    public CmdResult exec(String cmd, long timeout, TimeUnit unit) {
        try {
            CmdResult cmdResult = new CmdResult();
            Runtime runtime = Runtime.getRuntime();

            Process process = runtime.exec(cmd);

            Future<List<String>> stdOut = poolExecutor.submit(new CmdResultReader(process, ResultType.STD_OUT));
            Future<List<String>> stdErr = poolExecutor.submit(new CmdResultReader(process, ResultType.STD_ERR));
            boolean success = process.waitFor(timeout, unit);
            if (success) {
                cmdResult.setExitValue(process.exitValue());
            } else {
                cmdResult.setExitValue(Integer.MIN_VALUE);
            }

            cmdResult.setStdOuts(stdOut.get());
            cmdResult.setStdErrs(stdErr.get());
            return cmdResult;
        } catch (Exception e) {
            throw new GeneralException("exec err", e);
        }

    }

}
