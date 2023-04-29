package io.github.kylinhunter.commons.clazz.agent.plugin;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Logger;

import io.github.kylinhunter.commons.clazz.agent.config.AgentArgsHelper;
import io.github.kylinhunter.commons.log.jul.JULManager;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 00:50
 **/
public class PluginManager {
    private static Logger log;

    private static final List<Plugin> plugins = new ArrayList<>();

    /**
     * @return void
     * @title initLog
     * @description
     * @author BiJi'an
     * @date 2023-04-22 00:47
     */
    private static void initLog() {
        JULManager.init();
        log = Logger.getLogger(PluginManager.class.toString());
    }

    /**
     * @return void
     * @title loadPlugins
     * @description
     * @author BiJi'an
     * @date 2023-04-22 00:47
     */
    private static void loadPlugins(Instrumentation inst) {

        ServiceLoader<Plugin> allPlugins = ServiceLoader.load(Plugin.class);
        log.info("load plugins  start");
        allPlugins.forEach(plugin -> {
            log.info("load plugins: " + plugin.getName());
            plugin.init(inst);
            plugins.add(plugin);
        });
        log.info("load plugins  end");
    }

    /**
     * @param agentArgs agentArgs
     * @param inst      inst
     * @return void
     * @title initialize
     * @description
     * @author BiJi'an
     * @date 2023-03-19 00:19
     */
    public static void initialize(String agentArgs, Instrumentation inst) {
        initLog();
        log.info("initialize ,agentArgs=" + agentArgs);
        AgentArgsHelper.init(agentArgs);
        loadPlugins(inst);
    }

}
