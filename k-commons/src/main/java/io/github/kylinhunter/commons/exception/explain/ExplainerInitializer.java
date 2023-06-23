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
package io.github.kylinhunter.commons.exception.explain;

import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.init.AbstractInitializer;
import io.github.kylinhunter.commons.init.ClassScanner;
import io.github.kylinhunter.commons.reflect.ObjectCreator;
import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * @author BiJi'an
 * @description
 * @date 2023-06-21 22:56
 */
public class ExplainerInitializer extends AbstractInitializer {

  public ExplainerInitializer(Set<String> pkgs) {
    super(pkgs);
  }

  public ExplainerInitializer(ClassScanner classScanner) {
    super(classScanner);
  }

  @Override
  public void init() throws InitException {

    Set<Class<? extends ExplainerSupplier>> classes =
        classScanner.getSubTypesOf(ExplainerSupplier.class);
    Explainers explainers = ExplainManager.getExplainers();
    for (Class<? extends ExplainerSupplier> clazz : classes) {
      try {
        if (!Modifier.isAbstract(clazz.getModifiers())) {
          explainers.add(ObjectCreator.create(clazz).get());
        }
      } catch (Exception e) {
        throw new InitException("init  explainer error " + clazz.getName(), e);
      }
    }
  }
}
