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
public class Service2 {
    private final Service1 service1;

    public void method11() {
        service1.method11();
    }

    public String method12(String id) {
        return service1.method12(id);
    }

    public Result method13(Param param) {
        return service1.method13(param);
    }

}