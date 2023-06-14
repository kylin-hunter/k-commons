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
package io.github.kylinhunter.commons.exception;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import io.github.kylinhunter.commons.exception.explain.ExplainManager;
import io.github.kylinhunter.commons.exception.explain.Explainer;
import io.github.kylinhunter.commons.exception.info.ErrInfos;

/**
 * @author BiJi'an
 * @description
 * @date 2021/8/1
 */
public class ExceptionConvertor {

  /**
   * @param exception source
   * @return KRuntimeException
   * @title convert
   * @description
   * @author BiJi'an
   * @date 2022-05-18 00:30
   */
  public static KRuntimeException convert(Throwable exception) {
    return convert(exception, true);
  }

  /**
   * @param exception source
   * @param withCause withCause
   * @return KRuntimeException
   * @title convert
   * @description
   * @author BiJi'an
   * @date 2022-05-18 00:30
   */
  @SuppressFBWarnings("BC_UNCONFIRMED_CAST")
  public static KRuntimeException convert(Throwable exception, boolean withCause) {
    try {
      if (KRuntimeException.class.isAssignableFrom(exception.getClass())) {
        return (KRuntimeException) exception;
      }

      Explainer.ExplainResult explainResult = ExplainManager.explain(exception);
      if (withCause) {
        return new KRuntimeException(
            explainResult.getErrInfo(),
            explainResult.getExtra(),
            explainResult.getMsg(),
            exception);
      } else {
        return new KRuntimeException(
            explainResult.getErrInfo(), explainResult.getExtra(), explainResult.getMsg());
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return new KRuntimeException(ErrInfos.UNKNOWN, exception.getMessage());
  }
}
