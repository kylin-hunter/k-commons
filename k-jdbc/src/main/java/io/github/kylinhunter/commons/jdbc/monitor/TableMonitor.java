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
package io.github.kylinhunter.commons.jdbc.monitor;

import io.github.kylinhunter.commons.jdbc.monitor.task.RowListener;

public interface TableMonitor {

  /**
   * @title reset
   * @description reset
   * @author BiJi'an
   * @date 2023-12-17 00:41
   */
  void reset();

  /**
   * @title start
   * @description start
   * @author BiJi'an
   * @date 2023-12-17 00:41
   */
  void start();

  /**
   * @param rowListener rowListener
   * @title setRowListener
   * @description setRowListener
   * @author BiJi'an
   * @date 2023-12-25 23:44
   */
  void setRowListener(RowListener rowListener);

  /**
   * @title shutdown
   * @description shutdown
   * @author BiJi'an
   * @date 2023-12-25 23:44
   */
  void shutdown();
}
