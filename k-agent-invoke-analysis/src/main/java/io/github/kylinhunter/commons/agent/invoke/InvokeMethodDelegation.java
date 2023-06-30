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
package io.github.kylinhunter.commons.agent.invoke;

import io.github.kylinhunter.commons.agent.invoke.trace.InvokeTrace;
import io.github.kylinhunter.commons.agent.invoke.trace.InvokeTraceManager;
import io.github.kylinhunter.commons.sys.KGenerated;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 10:36
 */
@KGenerated
public class InvokeMethodDelegation {

  private static final InvokeTraceManager invokeTraceManager = InvokeTraceManager.getInstance();

  @RuntimeType
  public static Object intercept(
      @Origin Method method, @AllArguments Object[] arguments, @SuperCall Callable<?> callable)
      throws Exception {
    Thread thread = Thread.currentThread();
    InvokeTrace invokeTrace = new InvokeTrace(thread.getId(), thread.getStackTrace(), method);

    try {
      return callable.call();
    } finally {

      invokeTrace.end();
      invokeTraceManager.addTrace(invokeTrace);
    }
  }
}
