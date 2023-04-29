package io.github.kylinhunter.commons.agent.invoke;

import java.lang.reflect.Method;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 22:24
 **/
@Data
public class InvokeTrace {
    private final long threadId;
    private final StackTraceElement stacks[];
    private final Method method;
    private long start = System.currentTimeMillis();

    private long cost;

    public void end() {
        this.cost = System.currentTimeMillis() - start;
    }

    @Override
    public String toString() {
        final Class<?> declaringClass = method.getDeclaringClass();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(declaringClass.getSimpleName() + "." + method.getName() + "\n");

        for (int i = 0; i < stacks.length; i++) {
            StackTraceElement stack = stacks[i];
            stringBuilder.append(stack.getClassName() + ".");
            stringBuilder.append(stack.getMethodName() + "(" + stack.getLineNumber() + ") \n");

        }
        stringBuilder.append("with cost " + this.cost + " ms\n");

        return stringBuilder.toString();
    }
}
