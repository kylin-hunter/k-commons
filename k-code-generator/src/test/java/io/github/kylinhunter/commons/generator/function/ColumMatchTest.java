package io.github.kylinhunter.commons.generator.function;

import io.github.kylinhunter.commons.collections.MapUtils;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ColumMatchTest {

  @Test
  void execute() {
    ExpressionExecutor expressionExecutor = new ExpressionExecutor();

    Map<String, Object> env = MapUtils.newHashMap();
    env.put("name", "test");
    env.put("size", 10);
    env.put("precision", 11);

    Object execute = expressionExecutor.execute("(name=='test' && size>0 && precision>10)", env);
    System.out.println(execute);

    execute = expressionExecutor.execute("(name=='test' && size>0 && precision>11)", env);
    System.out.println(execute);
  }
}
