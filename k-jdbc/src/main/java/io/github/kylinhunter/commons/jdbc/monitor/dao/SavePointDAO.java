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
package io.github.kylinhunter.commons.jdbc.monitor.dao;

import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.SavePoint;

public interface SavePointDAO {

  /**
   * @param savePoint savePoint
   * @title save
   * @description save
   * @author BiJi'an
   * @date 2023-12-09 00:14
   */
  void save(SavePoint savePoint);

  /**
   * @return io.github.kylinhunter.commons.jdbc.monitor.dao.entity.SavePoint
   * @title get
   * @description get
   * @author BiJi'an
   * @date 2023-12-09 00:14
   */
  SavePoint get();

  /**
   * @param savePoint savePoint
   * @title update
   * @description update
   * @author BiJi'an
   * @date 2023-12-09 00:14
   */
  void update(SavePoint savePoint);

  /**
   * @title ensureTableExists
   * @description ensureTableExists
   * @author BiJi'an
   * @date 2023-12-09 00:14
   */
  void ensureTableExists();
}
