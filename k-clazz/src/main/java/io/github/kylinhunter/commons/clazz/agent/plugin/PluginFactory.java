package io.github.kylinhunter.commons.clazz.agent.plugin;

import java.util.ArrayList;
import java.util.List;

import io.github.kylinhunter.commons.clazz.agent.plugin.mca.MethodCallAnalysisPlugin;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-07 00:50
 **/
public class PluginFactory {

    public static List<IPlugin> pluginGroup = new ArrayList<>();

    static {
        pluginGroup.add(new MethodCallAnalysisPlugin());
    }
}
