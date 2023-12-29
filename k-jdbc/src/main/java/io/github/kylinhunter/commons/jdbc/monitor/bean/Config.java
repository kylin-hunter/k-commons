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
package io.github.kylinhunter.commons.jdbc.monitor.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-24 21:06
 */
@Getter
@Setter
@ToString
public class Config {

  private int threadPoolSize = 2; // thread pool size  for schedule

  private int execInterval = 3000; // interval for execute

  private int retryInterval = 5000; // re-try interval

  private int errorInterval = 10000; // error interval

  private int execBefore = 3000; // execute before some time

  private int tryMinTime = 5000; // try min time

  private int processTimeout = 60 * 1000; // process timeout

  private int maxRetryTimes = 3; // max retry times
}
