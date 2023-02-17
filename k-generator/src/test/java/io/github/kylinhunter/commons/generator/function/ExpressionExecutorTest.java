package io.github.kylinhunter.commons.generator.function;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.component.CF;

class ExpressionExecutorTest {

    @Test
    void execute() {
        ExpressionExecutor expressionExecutor = CF.get(ExpressionExecutor.class);

        Object execute = expressionExecutor.execute("k_string.to_uppercase('bijian')");
        System.out.println(execute);
    }
}