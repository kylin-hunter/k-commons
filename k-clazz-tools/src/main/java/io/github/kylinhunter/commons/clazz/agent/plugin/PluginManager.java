package io.github.kylinhunter.commons.clazz.agent.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Consumer;
import java.util.logging.Logger;

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
        init();
    }

    private static void init() {
        ServiceLoader<Plugin> allPlugins = ServiceLoader.load(Plugin.class);
        log.info("init plugins ");
        allPlugins.forEach(plugin -> {
            log.info("init plugins: " + plugin.getName());
            PluginManager.plugins.add(plugin);
        });
    }

    /**
     * @param action action
     * @return void
     * @title forEach
     * @description
     * @author BiJi'an
     * @date 2023-03-19 00:14
     */
    public static void forEach(Consumer<Plugin> action) {
        plugins.forEach(action);
    }

}
