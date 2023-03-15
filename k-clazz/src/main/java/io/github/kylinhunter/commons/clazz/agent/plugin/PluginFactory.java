package io.github.kylinhunter.commons.clazz.agent.plugin;

import java.util.ArrayList;
import java.util.List;

import io.github.kylinhunter.commons.clazz.agent.plugin.mca.InvokeAnalysisPlugin;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 00:50
 **/
public class PluginFactory {

    public static List<IPlugin> pluginGroup = new ArrayList<>();

    static {
        pluginGroup.add(new InvokeAnalysisPlugin());
    }
}
