package io.github.kylinhunter.commons.agent.invoke;

import io.github.kylinhunter.commons.clazz.agent.plugin.AbstractAgentTransformer;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.bean.DebugConfig;
import io.github.kylinhunter.commons.clazz.exception.AgentException;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.util.ThreadHelper;
import java.io.File;
import java.io.IOException;
import java.security.ProtectionDomain;
import java.util.concurrent.TimeUnit;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.utility.JavaModule;
import org.apache.commons.lang3.StringUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 00:40
 **/
public class InvokeTransformer extends AbstractAgentTransformer {

    public InvokeTransformer() {
    }

    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription,
                                            ClassLoader classLoader, JavaModule module,
                                            ProtectionDomain protectionDomain) {
        builder = builder.method(pluginPoint.getMethodMatcher())
                .intercept(MethodDelegation.to(InvokeMethodDelegation.class));
        debug(builder);
        String         path = "org/apache/commons/io/FileUtils.class";


        ThreadHelper.sleep(100, TimeUnit.MILLISECONDS);
        return builder;
    }

    private void debug(DynamicType.Builder<?> builder) {
        try {
            DebugConfig debug = pluginConfig.getDebug();
            if (debug != null && debug.isEnabled()) {
                String classSaveDir = debug.getClassSaveDir();
                if (!StringUtils.isEmpty(classSaveDir)) {
                    File dir = ResourceHelper.getDir(classSaveDir, ResourceHelper.PathType.FILESYSTEM, true);
                    builder.make().saveIn(dir);
                }
            }
        } catch (IOException e) {
            throw new AgentException("debug error", e);
        }

    }


}
