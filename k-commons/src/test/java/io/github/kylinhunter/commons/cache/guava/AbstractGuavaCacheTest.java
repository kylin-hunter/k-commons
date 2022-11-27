package io.github.kylinhunter.commons.cache.guava;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.util.TheadHelper;

public class AbstractGuavaCacheTest {

    @Test
    void testCache1() {
        Cache<Long> guavaCache = new TestCache1Imp();

        Long value;

        int i = 0;
        while ((i++) < 10) {

            value = guavaCache.get(new Integer(1), new Integer(2));
            System.out.println(value);
            TheadHelper.sleep(100, TimeUnit.MILLISECONDS);
        }
        guavaCache.put(100L, new Integer(1), new Integer(2));
        value = guavaCache.get(new Integer(1), new Integer(2));
        System.out.println(value);
        guavaCache.invalidate(new Integer(1), new Integer(2));
        value = guavaCache.get(new Integer(1), new Integer(2));
        System.out.println(value);

    }

    @Test
    void testCache2() {
        Cache<Long> guavaCache = new TestCache1Imp();

        Long value;

        int i = 0;
        while ((i++) < 10) {

            value = guavaCache.get(new TestParam1("1"), new TestParam2("parm2"));
            System.out.println(value);
            TheadHelper.sleep(100, TimeUnit.MILLISECONDS);
        }
        guavaCache.put(100L, new TestParam1("1"), new TestParam2("parm2"));
        value = guavaCache.get(new TestParam1("1"), new TestParam2("parm2"));
        System.out.println(value);
        guavaCache.invalidate(new TestParam1("1"), new TestParam2("parm2"));
        value = guavaCache.get(new TestParam1("1"), new TestParam2("parm2"));
        System.out.println(value);

    }

}

