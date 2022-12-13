package io.github.kylinhunter.commons.uid;

import org.junit.jupiter.api.Test;

class UidGeneratorTest {

    @Test
    void nextId() {

        UidGenerator worker = new UidGenerator(3, 4, 1);
        for (int i = 0; i < 10; i++) {
            long nextId = worker.nextId();
            System.out.println(nextId + "=>" + worker.parse(nextId));
        }

    }
}