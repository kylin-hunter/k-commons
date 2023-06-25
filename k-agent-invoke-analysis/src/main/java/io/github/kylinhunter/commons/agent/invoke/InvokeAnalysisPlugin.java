/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.agent.invoke;

import io.github.kylinhunter.commons.clazz.agent.plugin.AbstractPlugin;
import io.github.kylinhunter.commons.clazz.agent.plugin.AgentTransformer;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.bean.PluginConfig;

/**
 * -javaagent:/Users/bijian/workspace_gitee/k-commons/k-agent-invoke-analysis/build/libs/k-agent-invoke-analysis-1.0
 * .1.jar=config-file=/Users/bijian/workspace_gitee/k-commons/k-agent-invoke-analysis/src/main/resources/k-agent
 * -plugin-invoke-analysis.properties
 *
 * @author BiJi'an
 * @description
 * @date 2023-03-11 00:48
 */
public class InvokeAnalysisPlugin extends AbstractPlugin {

  public InvokeAnalysisPlugin() {
    super("invoke-analysis");
  }

  @Override
  public Class<? extends PluginConfig> configDefinition() {
    return InvokeAnalysisConfig.class;
  }

  @Override
  public Class<? extends AgentTransformer> transformerDefinition() {
    return InvokeTransformer.class;
  }
}
