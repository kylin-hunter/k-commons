package io.github.kylinhunter.commons.agent.invoke;

import java.io.IOException;
import java.security.ProtectionDomain;

import io.github.kylinhunter.commons.clazz.agent.plugin.AbstractAgentTransformer;
import io.github.kylinhunter.commons.exception.embed.GeneralException;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.utility.JavaModule;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 00:40
 **/
public class InvokeTransformer extends AbstractAgentTransformer {

    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription,
                                            ClassLoader classLoader, JavaModule module,
                                            ProtectionDomain protectionDomain) {
        builder = builder.method(pluginPoint.getMethodMatcher()).
                intercept(MethodDelegation.to(InvokeAnalysis.class));
        try {
            builder.make().saveIn(UserDirUtils.getTmpDir("bja"));
        } catch (IOException e) {
            throw new GeneralException("save to dir error", e);
        }
        return builder;
    }

}
