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
package io.github.kylinhunter.commons.uid;

import io.github.kylinhunter.commons.date.DateUtils;
import java.util.StringJoiner;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-13 12:41
 */
@RequiredArgsConstructor
@Getter
public class UidInfo {
  private final long sequence;
  private final long type;
  private final long workerId;
  private final long datacenterId;
  private final long timestamp;

  public String toString() {
    return new StringJoiner(", ", UidInfo.class.getSimpleName() + "[", "]")
        .add("sequence=" + sequence)
        .add("type=" + type)
        .add("workerId=" + workerId)
        .add("datacenterId=" + datacenterId)
        .add("timestamp=" + timestamp + "/" + DateUtils.format(timestamp))
        .toString();
  }
}
