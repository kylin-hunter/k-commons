package io.github.kylinhunter.commons.clazz.agent.plugin.mca;

import java.lang.reflect.Method;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-15 17:24
 **/
@Data
public class InvokeTrace {
    private String threadId;
    long start = System.currentTimeMillis();
    private Method method;
    private StackTraceElement stacks[];
    private long cost;

    public InvokeTrace(StackTraceElement[] stacks, Method method) {
        Thread thread = Thread.currentThread();
        this.threadId =this.getThreadId();
        this.stacks = thread.getStackTrace();
        this.method = method;

    }

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
        return stringBuilder.toString();
    }
}
