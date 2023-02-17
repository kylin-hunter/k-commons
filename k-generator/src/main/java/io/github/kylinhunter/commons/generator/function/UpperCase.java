package io.github.kylinhunter.commons.generator.function;

import java.util.Map;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-17 01:00
 **/
public class UpperCase extends AbstractFunction {

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject str) {
        String string = FunctionUtils.getStringValue(str, env);
        return new AviatorString(string.toUpperCase());
    }

    public String getName() {
        return "k_string.to_uppercase";
    }
}
