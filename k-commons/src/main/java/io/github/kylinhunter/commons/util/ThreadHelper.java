package io.github.kylinhunter.commons.util;

import java.util.concurrent.TimeUnit;

import io.github.kylinhunter.commons.exception.embed.GeneralException;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2021/8/19
 **/
public class ThreadHelper {
    public static void sleep(int count, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(count);
        } catch (InterruptedException e) {
            throw new GeneralException("sleep error", e);
        }
    }
}
