package io.github.kylinhunter.commons.clazz.agent;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

import io.github.kylinhunter.commons.clazz.agent.plugin.PluginManager;
import io.github.kylinhunter.commons.util.OnceRunner;

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
     * @date 2023-03-11 23:09
     */
    public static void premain(String agentArgs, Instrumentation inst) throws IOException {


        OnceRunner.run(PreMain.class, () -> PluginManager.initialize(agentArgs, inst));

    }

}
