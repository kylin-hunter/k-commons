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
    public static void agentmain(String agentArgs, Instrumentation inst) throws IOException {


        OnceRunner.run(PreMain.class, () -> PluginManager.initialize(agentArgs, inst));
    }
}
