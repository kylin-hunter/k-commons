package io.github.kylinhunter.commons.generator.function;

import java.util.Map;

import com.googlecode.aviator.AviatorEvaluator;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CAfter;
import io.github.kylinhunter.commons.generator.exception.CodeException;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-17 01:05
 **/
@C
public class ExpressionExecutor {

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
        try {
            return (T) AviatorEvaluator.execute(expression);
        } catch (Exception e) {
            throw new CodeException("execute error:" + expression, e);
        }
    }

    /**
     * @param expression expression
     * @param env        env
     * @return T
     * @title execute
     * @description
     * @author BiJi'an
     * @date 2023-02-19 18:50
     */
    @SuppressWarnings("unchecked")
    public <T> T execute(final String expression, final Map<String, Object> env) {
        try {
            return (T) AviatorEvaluator.execute(expression, env);
        } catch (Exception e) {
            throw new CodeException("execute error:" + expression, e);
        }
    }

    @CAfter
    private void init() {
        AviatorEvaluator.addFunction(new StringToCamel());
        AviatorEvaluator.addFunction(new StringToLower());
    }
}
