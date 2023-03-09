package io.github.kylinhunter.commons.clazz.agent.plugin.mca.test;

import io.github.kylinhunter.commons.clazz.agent.plugin.mca.test.bean.Param;
import io.github.kylinhunter.commons.clazz.agent.plugin.mca.test.bean.Result;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-09 16:26
 **/
public class Service1Imp implements Service1 {

    public void method11() {
        System.out.println("method11");
    }

    public String method12(String id) {
        System.out.println("method12");
        return "method12" + id;
    }

    public Result method13(Param param) {
        System.out.println("method13");
        return new Result(param);
    }

}