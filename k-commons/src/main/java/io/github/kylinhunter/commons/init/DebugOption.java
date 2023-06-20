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
package io.github.kylinhunter.commons.init;

import io.github.kylinhunter.commons.io.file.UserDirUtils;
import java.io.File;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-05-27 00:22
 */
@Getter
@Setter
public class DebugOption {

  public static final DebugOption INSTANCE = new DebugOption();
  private File classSaveDir = UserDirUtils.getTmpDir("k-debug", "clazz");
}
