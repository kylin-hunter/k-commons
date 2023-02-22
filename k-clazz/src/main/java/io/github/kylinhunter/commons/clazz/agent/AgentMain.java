package io.github.kylinhunter.commons.clazz.agent;

import java.lang.instrument.Instrumentation;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-29 00:23
 **/
public class AgentMain {

    /**
     * @param agentArgs agentArgs
     * @param inst      inst
     * @return void
     * @title attach load
     * @description
     * @author BiJi'an
     * @date 2022-12-29 00:23
     */
    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("agentmain start");
        System.out.println(agentArgs);
    }
}
