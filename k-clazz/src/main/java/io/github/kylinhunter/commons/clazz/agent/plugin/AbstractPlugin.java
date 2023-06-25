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
package io.github.kylinhunter.commons.clazz.agent.plugin;

import io.github.kylinhunter.commons.clazz.agent.AgentListenr;
import io.github.kylinhunter.commons.clazz.agent.plugin.bean.PluginPoint;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.PluginConfigReader;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.bean.DebugConfig;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.bean.PluginConfig;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.bean.PointCut;
import io.github.kylinhunter.commons.collections.CollectionUtils;
import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.exception.check.ExceptionChecker;
import io.github.kylinhunter.commons.io.file.FileUtil;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import io.github.kylinhunter.commons.reflect.ObjectCreator;
import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.List;
import java.util.logging.Logger;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-13 19:15
 */
@Data
@RequiredArgsConstructor
public abstract class AbstractPlugin implements Plugin {

  private Logger logger = Logger.getLogger(AbstractPlugin.class.toString());
  private final List<PluginPoint> pluginPoints = ListUtils.newArrayList();
  private final AgentListenr defaultAgentListenr = new AgentListenr();
  private PluginConfigReader pluginConfigReader = new PluginConfigReader();
  private final String name;
  private PluginConfig config;

  /**
   * @return void
   * @title buildConfig
   * @description
   * @author BiJi'an
   * @date 2023-04-16 22:43
   */
  protected void buildConfig() {
    Class<? extends PluginConfig> configClazz = this.configDefinition();
    ExceptionChecker.checkNotNull(configClazz, "config definition can't be null");
    this.config = pluginConfigReader.read(configClazz, this.name);
    DebugConfig debug = this.config.getDebug();
    if (debug.isEnabled()) {
      String classSaveDir = debug.getClassSaveDir();
      if (!StringUtil.isEmpty(classSaveDir)) {
        File dir = FileUtil.getDir(true, classSaveDir);
        debug.setFileClassSaveDir(dir);
      } else {
        debug.setFileClassSaveDir(UserDirUtils.getTmpDir());
        System.out.println("====>" + UserDirUtils.getTmpDir());

      }

      if (debug.isClassSaveDirAutoClean()) {
        FileUtil.cleanDirectoryQuietly(debug.getFileClassSaveDir());
      }
    }
  }

  /**
   * @param pluginPoint pluginPoint
   * @return io.github.kylinhunter.commons.clazz.agent.plugin.AgentTransformer
   * @title createAgentTransformer
   * @description
   * @author BiJi'an
   * @date 2023-04-15 11:03
   */
  protected AgentTransformer buildTransformer(PluginPoint pluginPoint) {
    Class<? extends AgentTransformer> clazz = this.transformerDefinition();
    AgentTransformer agentTransformer = ObjectCreator.create(clazz);
    agentTransformer.init(this.config, pluginPoint);
    return agentTransformer;
  }

  /**
   * @return void
   * @title buildPluginPoint
   * @description
   * @author BiJi'an
   * @date 2023-04-15 11:03
   */
  protected void buildPluginPoints() {
    List<PointCut> pointCuts = config.getPoints();
    if (!CollectionUtils.isEmpty(pointCuts)) {
      for (PointCut pointCut : pointCuts) {
        PluginPoint pluginPoint = new PluginPoint();
        pluginPoint.setTypeMatcher(pointCut.getType().toElementMatcher());
        pluginPoint.setMethodMatcher(pointCut.getMethod().toElementMatcher());
        this.pluginPoints.add(pluginPoint);
      }
    }
  }

  /**
   * @return void
   * @title buildTransformer
   * @description
   * @author BiJi'an
   * @date 2023-04-15 11:30
   */
  protected void buildAgent(Instrumentation inst) {
    for (PluginPoint pluginPoint : pluginPoints) {
      AgentBuilder.Default builder = new AgentBuilder.Default();
      ElementMatcher<TypeDescription> typesMatcher = pluginPoint.getTypeMatcher();
      AgentTransformer agentTransformer = this.buildTransformer(pluginPoint);
      builder
          .type(typesMatcher)
          .transform(agentTransformer)
          .with(defaultAgentListenr)
          .installOn(inst);
    }
  }

  /**
   * @param inst inst
   * @return void
   * @title init
   * @description
   * @author BiJi'an
   * @date 2023-04-16 2:26
   */
  public void init(Instrumentation inst) {
    logger.info("init plugin[" + this.name + "]  start");
    this.buildConfig();
    this.buildPluginPoints();
    this.buildAgent(inst);
    logger.info("init plugin[" + this.name + "]  end");
  }
}
