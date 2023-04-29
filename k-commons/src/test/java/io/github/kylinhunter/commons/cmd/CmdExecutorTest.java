package io.github.kylinhunter.commons.cmd;

import io.github.kylinhunter.commons.juc.ThreadPoolExecutorFactory;
import java.util.List;
import org.junit.jupiter.api.Test;

class CmdExecutorTest {

    @Test
    void run() {
        CmdExecutor cmdExecutor = new CmdExecutor();
        cmdExecutor.setPoolExecutor(ThreadPoolExecutorFactory.register("1", 1, 2, 1));
        List<CmdResult> cmdResults = cmdExecutor.exec("pwd 1", "ls /");
        cmdResults.forEach(e -> {
            System.out.println(e.isSuccess());
            System.out.println("#stdOut");
            e.getStdOuts().forEach(System.out::println);
            System.out.println("#stdErr");
            e.getStdErrs().forEach(System.out::println);
            System.out.println("#end");

        });
    }
}