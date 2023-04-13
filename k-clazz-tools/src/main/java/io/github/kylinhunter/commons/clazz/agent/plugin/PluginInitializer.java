package io.github.kylinhunter.commons.clazz.agent.plugin;

import java.lang.instrument.Instrumentation;
import java.util.List;
import java.util.logging.Logger;

import io.github.kylinhunter.commons.clazz.agent.AgentListenr;
import io.github.kylinhunter.commons.clazz.agent.config.AgentArgsHelper;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.PluginConfig;
import io.github.kylinhunter.commons.exception.check.ExceptionChecker;
import io.github.kylinhunter.commons.reflect.ObjectCreator;
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
        PluginManager.forEach(plugin -> process(plugin, inst));
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
    private <T extends PluginConfig, V extends AgentTransformer> void process(Plugin<T,V> plugin,
                                                                              Instrumentation inst) {
        T pluginConfig = AgentArgsHelper.loadConfig(plugin);
        plugin.setConfig(pluginConfig);

        log.info(pluginConfig.toString());
        Class<V> transformerClazz = plugin.getTransformer();
        plugin.init();
        List <PluginPoint>pluginPoints =  plugin.getPluginPoints();

        for (PluginPoint pluginPoint :pluginPoints) {

            ElementMatcher<TypeDescription> typesMatcher = pluginPoint.getTypeMatcher();
            ExceptionChecker.checkNotNull(typesMatcher);
            ExceptionChecker.checkNotNull(pluginPoint);
            ExceptionChecker.checkNotNull(transformerClazz);
            AgentTransformer agentTransformer = ObjectCreator.create(transformerClazz);
            agentTransformer.setPluginPoint(pluginPoint);
            AgentBuilder.Default builder = new AgentBuilder.Default();
            builder.type(typesMatcher).transform(agentTransformer).with(agentListenr).installOn(inst);
        }

    }
}
