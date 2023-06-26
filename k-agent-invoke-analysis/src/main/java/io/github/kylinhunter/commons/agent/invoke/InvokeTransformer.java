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

import io.github.kylinhunter.commons.clazz.agent.plugin.AbstractAgentTransformer;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.bean.DebugConfig;
import io.github.kylinhunter.commons.clazz.exception.AgentException;
import io.github.kylinhunter.commons.sys.KGenerated;
import java.io.IOException;
import java.security.ProtectionDomain;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.utility.JavaModule;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 00:40
 */
@KGenerated
public class InvokeTransformer extends AbstractAgentTransformer {

  public InvokeTransformer() {}

  @Override
  public DynamicType.Builder<?> transform(
      DynamicType.Builder<?> builder,
      TypeDescription typeDescription,
      ClassLoader classLoader,
      JavaModule module,
      ProtectionDomain protectionDomain) {
    builder =
        builder
            .method(pluginPoint.getMethodMatcher())
            .intercept(MethodDelegation.to(InvokeMethodDelegation.class));
    debug(builder);

    return builder;
  }

  private void debug(DynamicType.Builder<?> builder) {
    try {
      DebugConfig debug = pluginConfig.getDebug();
      if (debug != null && debug.isEnabled()) {
        if (debug.getFileClassSaveDir() != null) {
          builder.make().saveIn(debug.getFileClassSaveDir());
        }
      }
    } catch (IOException e) {
      throw new AgentException("debug error", e);
    }
  }
}
