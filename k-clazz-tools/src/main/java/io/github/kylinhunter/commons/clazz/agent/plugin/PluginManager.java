package io.github.kylinhunter.commons.clazz.agent.plugin;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Logger;

import io.github.kylinhunter.commons.clazz.agent.config.AgentArgsHelper;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 00:50
 **/
@Data
public class PluginManager {
    private static Logger log = Logger.getLogger(PluginManager.class.toString());
    public static final List<Plugin> plugins = new ArrayList<>();

    static {
        loadPlugins();
    }

    private static void loadPlugins() {
        ServiceLoader<Plugin> allPlugins = ServiceLoader.load(Plugin.class);
        log.info("init plugins ");
        allPlugins.forEach(plugin -> {
            log.info("init plugins: " + plugin.getName());
            PluginManager.plugins.add(plugin);
        });
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
        log.info("premain start,agentArgs=" + agentArgs);
        AgentArgsHelper.init(agentArgs);
        plugins.forEach(plugin -> plugin.init(inst));
    }

}
