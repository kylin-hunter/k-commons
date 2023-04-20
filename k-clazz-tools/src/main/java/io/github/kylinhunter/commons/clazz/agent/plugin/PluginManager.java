package io.github.kylinhunter.commons.clazz.agent.plugin;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Logger;

import io.github.kylinhunter.commons.clazz.agent.config.AgentArgsHelper;
import io.github.kylinhunter.commons.log.jul.JULManager;
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
//        loadPlugins();
    }

    private static  int a=1;
    public static void loadPlugins() {
        ServiceLoader<Plugin> allPlugins = ServiceLoader.load(Plugin.class);
        log.info("load plugins  start"+(a++));
        allPlugins.forEach(plugin -> {
            log.info("load plugins: " + plugin.getName());
            PluginManager.plugins.add(plugin);
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

        JULManager.init();
        log.info("premain start,agentArgs=" + agentArgs);
        PluginManager.loadPlugins();
        AgentArgsHelper.init(agentArgs);
        plugins.forEach(plugin -> plugin.init(inst));
    }

}
