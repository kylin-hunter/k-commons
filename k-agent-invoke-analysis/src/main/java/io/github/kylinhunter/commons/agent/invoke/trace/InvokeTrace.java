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
package io.github.kylinhunter.commons.agent.invoke.trace;

import java.lang.reflect.Method;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 22:24
 */
@Data
public class InvokeTrace {

  private final long threadId;
  private final StackTraceElement[] stacks;
  private final Method method;
  private long start = System.currentTimeMillis();

  private long cost;

  public void end() {
    this.cost = System.currentTimeMillis() - start;
  }

  @Override
  public String toString() {
    final Class<?> declaringClass = method.getDeclaringClass();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(declaringClass.getSimpleName()).append(".").append(method.getName())
        .append("\n");

    for (StackTraceElement stack : stacks) {
      stringBuilder.append(stack.getClassName()).append(".");
      stringBuilder.append(stack.getMethodName()).append("(").append(stack.getLineNumber())
          .append(") \n");
    }
    stringBuilder.append("with cost ").append(this.cost).append(" ms\n");

    return stringBuilder.toString();
  }
}
