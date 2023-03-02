package io.github.kylinhunter.commons.cmd;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.juc.ThreadPools;

class CmdExecutorTest {

    @Test
    void run() {
        CmdExecutor cmdExecutor = new CmdExecutor();
        cmdExecutor.setPoolExecutor(ThreadPools.create(1, 2, 1));
        List<CmdResult> cmdResults = cmdExecutor.exec("pwd 1", "ls /");
        cmdResults.forEach(e->{
            System.out.println(e.isSuccess());
            System.out.println(e);
        });
    }
}