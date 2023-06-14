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
package io.github.kylinhunter.commons.generator.function;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CAfter;
import io.github.kylinhunter.commons.exception.ExCatcher;
import io.github.kylinhunter.commons.io.IOUtil;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.util.ObjectValues;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 01:05
 */
@C
public class ExpressionExecutor {

  /**
   * @param expression expression
   * @return java.lang.Object
   * @title execute
   * @description
   * @author BiJi'an
   * @date 2023-03-19 01:07
   */
  @SuppressWarnings("unchecked")
  public <T> T execute(final String expression) {
    return ExCatcher.run(() -> (T) AviatorEvaluator.execute(expression));
  }

  /**
   * @param expression expression
   * @param type type
   * @return T
   * @title execute
   * @description
   * @author BiJi'an
   * @date 2023-06-01 20:23
   */
  public <T> T execute(final String expression, Class<T> type) {
    return ExCatcher.run(() -> ObjectValues.get(AviatorEvaluator.execute(expression), type));
  }

  /**
   * @param expression expression
   * @param env env
   * @return T
   * @title execute
   * @description
   * @author BiJi'an
   * @date 2023-02-19 18:50
   */
  @SuppressWarnings("unchecked")
  public <T> T execute(final String expression, final Map<String, Object> env) {

    return (T) ExCatcher.run(() -> AviatorEvaluator.execute(expression, env, true));
  }

  /**
   * @param expression expression
   * @param env env
   * @param type type
   * @return T
   * @title execute
   * @description
   * @author BiJi'an
   * @date 2023-06-01 20:29
   */
  public <T> T execute(final String expression, final Map<String, Object> env, Class<T> type) {

    return ExCatcher.run(
        () -> ObjectValues.get(AviatorEvaluator.execute(expression, env, true), type));
  }

  @SuppressWarnings("unchecked")
  public <T> T executeByFile(final String path, final Map<String, Object> env) {
    return ExCatcher.run(
        () -> {
          try (InputStream inputStream = ResourceHelper.getInputStream(path)) {
            String text = IOUtil.toString(inputStream, StandardCharsets.UTF_8);
            Expression expression = AviatorEvaluator.getInstance().compile(path, text, true);
            return (T) expression.execute(env);
          }
        });
  }

  /**
   * @param path path
   * @param env env
   * @param type type
   * @return T
   * @title executeFromFile
   * @description
   * @author BiJi'an
   * @date 2023-06-01 20:53
   */
  public <T> T executeByFile(final String path, final Map<String, Object> env, Class<T> type) {

    return ExCatcher.run(() -> ObjectValues.get(executeByFile(path, env), type));
  }

  @CAfter
  private void init() {
    AviatorEvaluator.addFunction(new StringToCamel());
    AviatorEvaluator.addFunction(new StringToLower());
  }
}
