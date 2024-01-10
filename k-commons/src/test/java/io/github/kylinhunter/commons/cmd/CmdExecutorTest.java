package io.github.kylinhunter.commons.cmd;

import io.github.kylinhunter.commons.os.OS;
import io.github.kylinhunter.commons.os.OSUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CmdExecutorTest {

  @Test
  void run() {
    CmdExecutor cmdExecutor = new CmdExecutor();
    OS os = OSUtils.os();
    if (os == OS.MAC) {
      ExecResult execResult = cmdExecutor.exec("pwd", "1");

      System.out.println(execResult.isSuccess());
      Assertions.assertFalse(execResult.isSuccess());
      System.out.println("#stdOut");
      execResult.getStdOuts().forEach(System.out::println);
      System.out.println("#stdErr");
      execResult.getStdErrs().forEach(System.out::println);
      System.out.println("#end");

      execResult = cmdExecutor.exec("ls", "/");

      System.out.println(execResult.isSuccess());
      Assertions.assertTrue(execResult.isSuccess());

      System.out.println("#stdOut");
      execResult.getStdOuts().forEach(System.out::println);
      System.out.println("#stdErr");
      execResult.getStdErrs().forEach(System.out::println);
      System.out.println("#end");

    }
    cmdExecutor.close();
  }
}
