package io.github.kylinhunter.commons.clazz;

import java.util.concurrent.TimeUnit;

import io.github.kylinhunter.commons.clazz.agent.PreMain;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-29 00:44
 **/
public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main");
        System.out.println(PreMain.class.getName());
        if (args.length > 0) {

            TimeUnit.SECONDS.sleep(Integer.parseInt(args[0]));
        }

    }
}
