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

import io.github.kylinhunter.commons.sys.KGenerated;
import java.util.logging.Logger;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;
import net.bytebuddy.utility.nullability.MaybeNull;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 23:12
 */
@KGenerated
@SuppressWarnings("NullableProblems")
public class AgentListenr implements AgentBuilder.Listener {

  public static final Logger log = Logger.getLogger(AgentListenr.class.toString());

  @Override
  public void onDiscovery(
      String typeName,
      @MaybeNull ClassLoader classLoader,
      @MaybeNull JavaModule module,
      boolean loaded) {
    // log.info("onDiscovery：" + typeName);
  }

  @Override
  public void onTransformation(
      TypeDescription typeDescription,
      @MaybeNull ClassLoader classLoader,
      @MaybeNull JavaModule module,
      boolean loaded,
      DynamicType dynamicType) {
    log.info("onTransformation：" + typeDescription);
  }

  @Override
  public void onIgnored(
      TypeDescription typeDescription,
      @MaybeNull ClassLoader classLoader,
      @MaybeNull JavaModule module,
      boolean loaded) {
    // log.info("onIgnored：" + typeDescription);
  }

  @Override
  public void onError(
      String typeName,
      @MaybeNull ClassLoader classLoader,
      @MaybeNull JavaModule module,
      boolean loaded,
      Throwable throwable) {
    log.info("onError：" + typeName + "=>" + throwable.getMessage());
    throwable.printStackTrace();
  }

  @Override
  public void onComplete(
      String typeName,
      @MaybeNull ClassLoader classLoader,
      @MaybeNull JavaModule module,
      boolean loaded) {
    // log.info("onComplete：" + typeName);
  }
}
