package io.github.kylinhunter.commons.clazz.agent;

import java.util.concurrent.Callable;

import net.bytebuddy.implementation.bind.annotation.SuperCall;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-07 19:54
 **/
public class Target {

    public static String aaa(@SuperCall Callable<String> zuper) throws Exception {
        return zuper.call() + "Hello!";
    }
}
