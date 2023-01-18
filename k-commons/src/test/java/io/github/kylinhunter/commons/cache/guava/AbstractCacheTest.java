package io.github.kylinhunter.commons.cache.guava;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.util.ThreadHelper;

public class AbstractCacheTest {

    @Test
    void testCache1() {
        Cache<Long> guavaCache = new TestCache1Imp();

        Long value;

        int i = 0;
        while ((i++) < 10) {

            value = guavaCache.get(new Integer(1), new Integer(2));
            System.out.println(value);
            ThreadHelper.sleep(100, TimeUnit.MILLISECONDS);
        }
        guavaCache.put(new Object[] {new Integer(1), new Integer(2)}, 100L);
        value = guavaCache.get(new Integer(1), new Integer(2));
        System.out.println(value);
        guavaCache.invalidate(new Integer(1), new Integer(2));
        value = guavaCache.get(new Integer(1), new Integer(2));
        System.out.println(value);

    }

    @Test
    void testCache2() {
        TestCache2 guavaCache = new TestCache2Imp();

        Long value;

        int i = 0;
        while ((i++) < 10) {

            value = guavaCache.get(new TestParam1("1"), new TestParam2("parm2"));
            System.out.println(value);
            ThreadHelper.sleep(100, TimeUnit.MILLISECONDS);
        }
        guavaCache.put(new TestParam1("1"), new TestParam2("parm2"), 100L);

        value = guavaCache.get(new TestParam1("1"), new TestParam2("parm2"));
        System.out.println(value);
        guavaCache.invalidate(new TestParam1("1"), new TestParam2("parm2"));
        value = guavaCache.get(new TestParam1("1"), new TestParam2("parm2"));
        System.out.println(value);

    }

}

