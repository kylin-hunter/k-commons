package io.github.kylinhunter.commons.jmx;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-07 00:47
 **/
public class GCUtils {

    /**
     * @return void
     * @title printGCInfo
     * @description
     * @author BiJi'an
     * @date 2023-03-09 15:00
     */
    public static void printGCInfo() {
        List<GarbageCollectorMXBean> garbages = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean garbage : garbages) {
            String info = String.format("name: %s\t count:%s\t took:%s\t pool name:%s",
                    garbage.getName(),
                    garbage.getCollectionCount(),
                    garbage.getCollectionTime(),
                    Arrays.deepToString(garbage.getMemoryPoolNames()));
            System.out.println(info);
        }
    }

    public static void main(String[] args) {
        GCUtils.printGCInfo();
    }
}
