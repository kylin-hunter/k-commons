package io.github.kylinhunter.commons.clazz;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.math.NumberUtils;

import io.github.kylinhunter.commons.clazz.agent.PreMain;
import io.github.kylinhunter.commons.util.ThreadHelper;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-29 00:44
 **/
public class Main {
    public static void main(String[] args) {
        System.out.println("main");
        System.out.println(PreMain.class.getName());
        if (args.length > 0) {

            ThreadHelper.sleep(NumberUtils.toInt(args[0]), TimeUnit.SECONDS);
        }

    }
}
