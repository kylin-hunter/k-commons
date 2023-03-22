package io.github.kylinhunter.commons.clazz.agent.plugin;

import java.lang.instrument.Instrumentation;
import java.util.logging.Logger;

import io.github.kylinhunter.commons.clazz.agent.AgentListenr;
import io.github.kylinhunter.commons.clazz.agent.config.AgentArgsHelper;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.PluginConfig;
import io.github.kylinhunter.commons.clazz.agent.plugin.invoke.InvokeTransformer;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 00:16
 **/
public class PluginInitializer {
    public static Logger log = Logger.getLogger(PluginInitializer.class.toString());
    private final AgentListenr agentListenr = new AgentListenr();

    /**
     * @param agentArgs agentArgs
     * @param inst      inst
     * @return void
     * @title initialize
     * @description
     * @author BiJi'an
     * @date 2023-03-19 00:19
     */
    public void initialize(String agentArgs, Instrumentation inst) {

        log.info("premain start,agentArgs=" + agentArgs);
        AgentArgsHelper.init(agentArgs);
        PluginCenter.forEach(plugin -> process(plugin, inst));
    }

    /**
     * @param plugin plugin
     * @param inst   inst
     * @return void
     * @title process
     * @description
     * @author BiJi'an
     * @date 2023-03-19 15:46
     */
    private void process(Plugin plugin, Instrumentation inst) {
        PluginConfig pluginConfig = AgentArgsHelper.getConfig(PluginConfig.class);
        log.info(pluginConfig.toString());

        PluginConfig1[] pluginConfig1s = plugin.buildInterceptPoint();
        if (pluginConfig1s != null) {
            for (PluginConfig1 pluginConfig1 : pluginConfig1s) {
                if (pluginConfig1 != null) {
                    AgentBuilder.Default builder = new AgentBuilder.Default();
                    ElementMatcher<TypeDescription> typeDescriptionElementMatcher = pluginConfig1.buildTypesMatcher();
                    if (typeDescriptionElementMatcher != null) {
                        builder.type(typeDescriptionElementMatcher)
                                .transform(new InvokeTransformer(pluginConfig1)
                                ).with(agentListenr).installOn(inst);
                    }
                }

            }
        }

        plugin.other();
    }
}
