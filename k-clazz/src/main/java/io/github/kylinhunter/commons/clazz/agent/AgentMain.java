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
package io.github.kylinhunter.commons.clazz.agent;

import io.github.kylinhunter.commons.clazz.agent.plugin.PluginManager;
import io.github.kylinhunter.commons.util.OnceRunner;
import java.io.IOException;
import java.lang.instrument.Instrumentation;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-29 00:23
 */
public class AgentMain {

  /**
   * @param agentArgs agentArgs
   * @param inst inst
   * @return void
   * @title attach load
   * @description
   * @author BiJi'an
   * @date 2022-12-29 00:23
   */
  public static void agentmain(String agentArgs, Instrumentation inst) throws IOException {

    OnceRunner.run(PreMain.class, () -> PluginManager.initialize(agentArgs, inst));
  }
}
