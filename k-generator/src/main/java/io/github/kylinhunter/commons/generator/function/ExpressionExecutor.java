package io.github.kylinhunter.commons.generator.function;

import com.googlecode.aviator.AviatorEvaluator;

import io.github.kylinhunter.commons.component.C;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-17 01:05
 **/
@C
public class ExpressionExecutor {
    static {
        AviatorEvaluator.addFunction(new UpperCase());
    }

    /**
     * @param expression expression
     * @return java.lang.Object
     * @title execute
     * @description
     * @author BiJi'an
     * @date 2023-02-17 01:07
     */
    @SuppressWarnings("unchecked")
    public <T> T execute(final String expression) {
        return (T) AviatorEvaluator.execute(expression);
    }
}
