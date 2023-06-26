package io.github.kylinhunter.commons.agent.invoke.trace;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;

class InvokeTraceManagerTest {

  @Test
  public void test() throws NoSuchMethodException, InterruptedException {
    Thread thread1 = new Thread(() -> {
      Thread thread = Thread.currentThread();
      InvokeTraceManager instance = InvokeTraceManager.getInstance();
      Method method = null;
      try {
        method = InvokeTraceManagerTest.class.getMethod("test");
      } catch (NoSuchMethodException e) {
        throw new RuntimeException(e);
      }
      InvokeTrace invokeTrace = new InvokeTrace(thread.getId(), thread.getStackTrace(), method);
      instance.addTrace(invokeTrace);

    });
    thread1.start();
    thread1.join();

    InvokeTraceManager.getInstance()
        .getTraces()
        .forEach(
            (k, v) -> {
              System.out.println("trace id=>" + k);
              v.forEach(
                  t -> {
                    System.out.println(t);
                  });
              System.out.println("===trace end with time ===");
            });

  }

}