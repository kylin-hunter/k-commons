package io.github.kylinhunter.commons.clazz.monitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-07 00:50
 **/
public class PluginFactory {

    public static List<IPlugin> pluginGroup = new ArrayList<>();

    static {
        //链路监控
        pluginGroup.add(new LinkPlugin());
        //Jvm监控
        pluginGroup.add(new JvmPlugin());
    }
}
