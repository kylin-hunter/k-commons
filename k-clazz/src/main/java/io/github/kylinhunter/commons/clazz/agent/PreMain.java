package io.github.kylinhunter.commons.clazz.agent;

import java.lang.instrument.Instrumentation;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-29 00:23
 **/
public class PreMain {

    /**
     * @param agentArgs agentArgs
     * @param inst      inst
     * @return void
     * @title java -javaagent:agent.jar=agentArgs
     * @description
     * @author BiJi'an
     * @date 2022-12-29 00:23
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain start");
        System.out.println(agentArgs);
    }

}
