package io.github.kylinhunter.commons.clazz.monitor;

import net.bytebuddy.asm.Advice;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-07 00:48
 **/
public class JvmAdvice {


    @Advice.OnMethodExit()
    public static void exit() {
        JvmStack.printMemoryInfo();
        JvmStack.printGCInfo();
    }

}
