package io.github.kylinhunter.commons.clazz.agent.plugin.mca.test;

import io.github.kylinhunter.commons.clazz.agent.plugin.mca.test.bean.Param;
import io.github.kylinhunter.commons.clazz.agent.plugin.mca.test.bean.Result;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-09 16:26
 **/
@RequiredArgsConstructor
public class Service3 {
    private final Service2 service2;

    public void method11() {
        proxyMethod11();
    }

    public void proxyMethod11() {
        service2.method11();
    }

    public String method12(String id) {
        return proxyMethod12(id);
    }

    public String proxyMethod12(String id) {
        return service2.method12(id);
    }

    public Result method13(Param param) {
        return proxyMethod13(param);
    }

    public Result proxyMethod13(Param param) {
        return service2.method13(param);
    }

}