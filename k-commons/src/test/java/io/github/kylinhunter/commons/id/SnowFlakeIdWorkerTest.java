package io.github.kylinhunter.commons.id;

import org.junit.jupiter.api.Test;

class SnowFlakeIdWorkerTest {

    @Test
    void nextId() {

        SnowFlakeIdWorker worker = new SnowFlakeIdWorker(18, 2, 2, 1, 1);
        for (int i = 0; i < 30; i++) {
            System.out.println(worker.nextId());
        }

    }
}