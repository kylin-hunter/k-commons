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
        env.put("class.hasSuperClass", true);
        System.out.println("lombok_enabled=true");
        Object execute = expressionExecutor.executeFile("$user.dir$/templates/lombok_annotations.av", env);
        System.out.println(execute);

        env.put("class.hasSuperClass", false);
        System.out.println("lombok_enabled=false");
        execute = expressionExecutor.execute("$user.dir$/templates/lombok_annotations.av", env);
        System.out.println(execute);


    }
}