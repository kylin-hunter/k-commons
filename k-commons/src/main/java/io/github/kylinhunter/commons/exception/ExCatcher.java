package io.github.kylinhunter.commons.exception;

import io.github.kylinhunter.commons.exception.embed.biz.BizException;
import io.github.kylinhunter.commons.reflect.ObjectCreator;

public class ExCatcher {
    public static <R> R run(Supplier<R> supplier) {
        return run(BizException.class, supplier);
    }

    public static <T extends RuntimeException, R> R run(Class<T> clazz, Supplier<R> supplier) {

        try {
            return supplier.get();
        } catch (Throwable e) {
            throw ObjectCreator.create(clazz, new Class[] {Throwable.class}, new Object[] {e});
        }
    }

    @FunctionalInterface
    public interface Supplier<T> {
        T get() throws Throwable;
    }
}
