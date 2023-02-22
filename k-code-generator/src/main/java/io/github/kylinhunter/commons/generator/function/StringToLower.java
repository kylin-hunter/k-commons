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
public class StringToLower extends AbstractFunction {

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject param1) {
        String name = FunctionUtils.getStringValue(param1, env);
        return new AviatorString(name.toLowerCase());
    }

    public String getName() {
        return "k.string_to_lower";
    }
}
