package io.github.kylinhunter.commons.generator.function;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Maps;

import io.github.kylinhunter.commons.component.CF;

class LombokTest {

    @Test
    void execute() {
        ExpressionExecutor expressionExecutor = CF.get(ExpressionExecutor.class);
        Map<String, Object> env = Maps.newHashMap();
        env.put("lombok_enabled", true);
        System.out.println("lombok_enabled=true");
        Object execute = expressionExecutor.executeFile("io/github/kylinhunter/commons/generator/lombok.av", env);
        System.out.println(execute);

        env.put("lombok_enabled", false);
        System.out.println("lombok_enabled=false");
        execute = expressionExecutor.execute("io/github/kylinhunter/commons/generator/lombok.av", env);
        System.out.println(execute);

    }
}