package io.github.kylinhunter.commons.generator.function;

import com.google.common.collect.Maps;
import io.github.kylinhunter.commons.component.CF;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExpressionExecutorTest {

  @Test
  void execute() {
    ExpressionExecutor expressionExecutor = CF.get(ExpressionExecutor.class);
    Long result1 = expressionExecutor.execute("1+1");
    Assertions.assertEquals(2, result1);

    int result2 = expressionExecutor.execute("1+1", int.class);
    Assertions.assertEquals(2, result2);

    Map<String, Object> env = Maps.newHashMap();
    env.put("a", 2);

    Long result3 = expressionExecutor.execute("a+1", env);
    Assertions.assertEquals(3, result3);

    int result4 = expressionExecutor.execute("a+1", env, int.class);
    Assertions.assertEquals(3, result4);
  }

  @Test
  void testExecuteByFile() {

    Map<String, Object> env = Maps.newHashMap();
    env.put("a", 2);
    env.put("b", 2);

    String path = "io/github/kylinhunter/commons/generator/function/Function.txt";
    ExpressionExecutor expressionExecutor = CF.get(ExpressionExecutor.class);
    Long result1 = expressionExecutor.executeByFile(path, env);
    Assertions.assertEquals(4, result1);

    int result2 = expressionExecutor.executeByFile(path, env, int.class);
    Assertions.assertEquals(4, result2);
    System.out.println("result2=>" + result2);


  }

  @Test
  void executeFile() {
  }
}