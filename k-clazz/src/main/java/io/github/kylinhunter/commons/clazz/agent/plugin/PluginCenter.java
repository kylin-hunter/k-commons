package io.github.kylinhunter.commons.clazz.agent.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import io.github.kylinhunter.commons.clazz.agent.plugin.invoke.InvokeAnalysisPlugin;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 00:50
 **/
@Data
public class PluginCenter {

    public static final List<Plugin> plugins = new ArrayList<>();

    static {
        plugins.add(new InvokeAnalysisPlugin());
    }

    /**
     * @param action action
     * @return void
     * @title forEach
     * @description
     * @author BiJi'an
     * @date 2023-03-22 00:14
     */
    public static void forEach(Consumer<Plugin> action) {
        plugins.forEach(action);
    }

}
