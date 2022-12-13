package io.github.kylinhunter.commons.id;

import org.junit.jupiter.api.Test;

class SnowFlakeIdWorkerTest {

    @Test
    void nextId() {

        SnowFlakeIdWorker worker = new SnowFlakeIdWorker(18, 2, 2, 2, 1);
        for (int i = 0; i < 300; i++) {
            long nextId = worker.nextId();
            System.out.println(nextId);
            SnowFlakerInfo parse = worker.parse(nextId);
            System.out.println(parse);

        }

    }
}