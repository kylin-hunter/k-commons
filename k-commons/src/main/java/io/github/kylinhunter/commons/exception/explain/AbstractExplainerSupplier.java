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

import io.github.kylinhunter.commons.collections.ListUtils;
import java.util.List;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-24 02:11
 */
public abstract class AbstractExplainerSupplier implements ExplainerSupplier {

  @Getter
  private final List<Explainer> explainers = ListUtils.newArrayList();

  /**
   * @return io.github.kylinhunter.commons.source.explainer.Explainer
   * @title create
   * @description
   * @author BiJi'an
   * @date 2022-11-24 02:59
   */
  public <T extends Throwable> Explainer addExplainer(Class<T> clazz) {
    Explainer explainer = new Explainer(clazz);
    explainers.add(explainer);
    return explainer;
  }

  @Override
  public List<Explainer> get() {
    explain();
    return explainers;
  }

  public abstract void explain();
}
