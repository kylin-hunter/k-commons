package io.github.kylinhunter.commons.agent.invoke;

import io.github.kylinhunter.commons.clazz.agent.plugin.AbstractPlugin;
import io.github.kylinhunter.commons.clazz.agent.plugin.AgentTransformer;

/**
 * -javaagent:/Users/bijian/workspace_gitee/k-commons/k-agent-invoke-analysis/build/libs/k-agent-invoke-analysis-1.0
 * .1.jar=config-file=/Users/bijian/workspace_gitee/k-commons/k-agent-invoke-analysis/src/main/resources/k-agent
 * -plugin-invoke-analysis.properties
 *
 * @author BiJi'an
 * @description
 * @date 2023-03-11 00:48
 **/

public class InvokeAnalysisPlugin extends AbstractPlugin<InvokeAnalysisConfig> {
    public InvokeAnalysisPlugin() {
        super("invoke-analysis");
    }


    @Override
    public Class<? extends AgentTransformer> transformerDefinition() {
        return InvokeTransformer.class;
    }

}
