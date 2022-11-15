package com.kylinhunter.plat.commons.test;

import com.kylinhunter.plat.commons.exception.common.KRuntimeException;

/**
 * @author BiJi'an
 * @description 不抛出异常调用main，测试用的
 * @date 2022-01-01 15:06
 **/
public class MainTestInvoker {
    public static <T> void run(Invoker invoker) {
        try {
            invoker.invoke();
        } catch (Exception e) {
            throw new KRuntimeException(e);
        }
    }

    public interface Invoker {
        void invoke() throws Exception;
    }
}
