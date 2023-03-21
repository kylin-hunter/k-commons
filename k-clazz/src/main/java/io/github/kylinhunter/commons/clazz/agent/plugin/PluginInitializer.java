package io.github.kylinhunter.commons.clazz.agent.plugin;

import java.lang.instrument.Instrumentation;

import io.github.kylinhunter.commons.clazz.agent.AgentListenr;
import io.github.kylinhunter.commons.clazz.agent.config.AgentConfigReader;
import io.github.kylinhunter.commons.clazz.agent.plugin.invoke.InvokeTransformer;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-22 00:16
 **/
public class PluginInitializer {
    private final AgentListenr agentListenr = new AgentListenr();

    /**
     * @param agentArgs agentArgs
     * @param inst      inst
     * @return void
     * @title initialize
     * @description
     * @author BiJi'an
     * @date 2023-03-22 00:19
     */
    public void initialize(String agentArgs, Instrumentation inst) {
        System.out.println("premain start,agentArgs=" + agentArgs);
        AgentConfigReader.init(agentArgs);
        InvokeConfig invokeConfig = AgentConfigReader.loadConfig(InvokeConfig.class);
        System.out.println(invokeConfig);
        PluginCenter.forEach(plugin -> process(plugin, inst));
    }

    private void process(Plugin plugin, Instrumentation inst) {
        PluginConfig[] pluginConfigs = plugin.buildInterceptPoint();
        if (pluginConfigs != null) {
            for (PluginConfig pluginConfig : pluginConfigs) {
                if (pluginConfig != null) {
                    AgentBuilder.Default builder = new AgentBuilder.Default();
                    ElementMatcher<TypeDescription> typeDescriptionElementMatcher =
                            pluginConfig.buildTypesMatcher();
                    if (typeDescriptionElementMatcher != null) {
                        builder.type(typeDescriptionElementMatcher)
                                .transform(new InvokeTransformer(pluginConfig)
                                ).with(agentListenr).installOn(inst);
                    }
                }

            }
        }

        plugin.other();
    }
}
