package io.github.kylinhunter.commons.util;

import java.util.Set;

import org.assertj.core.util.Sets;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-07 14:18
 **/
public class OnceRunner {

    private static final Set<String> runNames = Sets.newHashSet();

    public static void run(Class<?> clazz, Runner r) {
        run(clazz.getName(), r);
    }

    public static void run(String name, Runner r) {
        if (r != null) {
            if (!runNames.contains(name)) {
                synchronized(OnceRunner.class) {
                    if (!runNames.contains(name)) {
                        r.run();
                        runNames.add(name);
                    }
                }

            }

        }

    }

    @FunctionalInterface
    interface Runner {
        void run();
    }
}

