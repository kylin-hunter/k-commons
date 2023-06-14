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
package io.github.kylinhunter.commons.clazz.agent.plugin.config.bean;

import io.github.kylinhunter.commons.collections.ListUtils;
import java.util.List;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 00:28
 */
@Data
public class PluginConfig {
  private List<PointCut> points = ListUtils.newArrayList();
  private DebugConfig debug;
}
