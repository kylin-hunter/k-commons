package io.github.kylinhunter.commons.clazz.agent.plugin.mca;

import io.github.kylinhunter.commons.clazz.agent.plugin.mca.test.Service1;
import io.github.kylinhunter.commons.clazz.agent.plugin.mca.test.Service1Imp;
import io.github.kylinhunter.commons.clazz.agent.plugin.mca.test.Service2;
import io.github.kylinhunter.commons.clazz.agent.plugin.mca.test.Service3;
import io.github.kylinhunter.commons.clazz.agent.plugin.mca.test.bean.Param;

class MethodCallAnalysisPluginTest {

    public static void main(String[] args) {

        Service1 service1 = new Service1Imp();
        Service2 service2=new Service2(service1);
        Service3 service3=new Service3(service2);
        service3.method11();
        service3.method12("id");
        service3.method13(new Param("1","2"));

    }
}