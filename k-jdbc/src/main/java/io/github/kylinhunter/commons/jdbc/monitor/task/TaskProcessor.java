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
package io.github.kylinhunter.commons.jdbc.monitor.task;

import java.util.concurrent.ScheduledExecutorService;

public interface TaskProcessor {

  /**
   * @title reset
   * @description reset
   * @author BiJi'an
   * @date 2023-12-29 22:20
   */
  void reset();

  /**
   * @title start
   * @description start
   * @author BiJi'an
   * @date 2023-12-29 22:20
   */
  void start();

  /**
   * @param scheduler scheduler
   * @title init
   * @description init
   * @author BiJi'an
   * @date 2023-12-29 22:20
   */
  void setScheduler(ScheduledExecutorService scheduler);

  /**
   * @param rowListener rowListener
   * @title setRowListener
   * @description setRowListener
   * @author BiJi'an
   * @date 2023-12-29 22:20
   */
  void setRowListener(RowListener rowListener);
}
