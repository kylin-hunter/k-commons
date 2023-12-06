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
package io.github.kylinhunter.commons.jdbc.binlog.savepoint;

import io.github.kylinhunter.commons.jdbc.binlog.savepoint.bean.SavePoint;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-25 02:51
 */
public interface SavePointManager {

  SavePoint DEAFULT_SAVEPOINT = new SavePoint("unknown", -1);


  void reset();

  void save(SavePoint savePoint);

  SavePoint getLatest();

  void init();

  /**
   * @param savePoint savePoint
   * @return boolean
   * @title isValid
   * @description isValid
   * @author BiJi'an
   * @date 2023-12-06 20:49
   */
  default boolean isValid(SavePoint savePoint) {
    return savePoint != null && !savePoint.equals(DEAFULT_SAVEPOINT);

  }


}
