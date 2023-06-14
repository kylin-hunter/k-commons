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
package io.github.kylinhunter.commons.io.file.reader;

import io.github.kylinhunter.commons.io.file.reader.FileStatLineProcessor.FileStat;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-05-18 01:31
 */
public class FileStatLineProcessor extends DefaultLineProcessor<FileStat> {
  private final FileStat fileStat = new FileStat();

  @Override
  public void process(String line) {
    fileStat.addLineNum();
  }

  @Override
  public FileStat getResult() {
    return fileStat;
  }

  @Data
  public static class FileStat {
    private int lineNum;

    public void addLineNum() {
      this.lineNum++;
    }
  }
}
