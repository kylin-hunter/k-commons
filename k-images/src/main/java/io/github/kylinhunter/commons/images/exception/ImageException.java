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
package io.github.kylinhunter.commons.images.exception;

import io.github.kylinhunter.commons.exception.embed.biz.BizException;
import io.github.kylinhunter.commons.exception.info.ErrInfo;

/**
 * @author BiJi'an
 * @description ImageException
 * @date 2023-01-17 23:47
 */
public class ImageException extends BizException {

  public ImageException(Throwable cause) {
    super(ImageErrInfos.IMAGE, "IMAGE ERROR", cause);
  }

  public ImageException(String message, Throwable cause) {
    super(ImageErrInfos.IMAGE, message, cause);
  }

  public ImageException(String message) {
    super(ImageErrInfos.IMAGE, message);
  }

  public ImageException(ErrInfo errInfo, String message, Throwable cause) {
    super(errInfo, message, cause);
  }

  public ImageException(ErrInfo errInfo, String message) {
    super(errInfo, message);
  }
}
