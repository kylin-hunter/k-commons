package io.github.kylinhunter.commons.util;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2021/8/19
 **/
public class TheadHelper {
    public static void sleep(int count, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
