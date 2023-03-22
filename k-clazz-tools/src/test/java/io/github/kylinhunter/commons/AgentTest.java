package io.github.kylinhunter.commons;

import java.util.List;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

class AgentTest {


   /*  mode 1
   java -javaagent:./k-clazz/build/libs/k-clazz-1.0.1.jar=agentArgs  -jar ./k-clazz/build/libs/k-clazz-1.0.1.jar 100
    */

    /*  mode 2
    java  -jar ./k-clazz/build/libs/k-clazz-1.0.1.jar 1000

     */
    public static void main(String[] args) throws Exception {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor virtualMachineDescriptor : list) {
            // 程序以cn.qz 开头
            final String displayName = virtualMachineDescriptor.displayName();
            System.out.println("displayName:" + displayName);
            boolean b = displayName.indexOf("k-clazz") > 0;
            if (b) {
                System.out.println(displayName + "\t" + virtualMachineDescriptor.id());
                VirtualMachine vm = VirtualMachine.attach(virtualMachineDescriptor.id());

                vm.loadAgent("/Users/bijian/workspace_gitee/k-commons/k-clazz/build/libs/k-clazz-1.0.1.jar",
                        "agentArgs");
                break;
            }
        }
    }
}