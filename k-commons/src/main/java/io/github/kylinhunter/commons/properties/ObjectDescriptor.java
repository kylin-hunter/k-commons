package io.github.kylinhunter.commons.properties;

import java.util.Map;

import io.github.kylinhunter.commons.collections.MapUtils;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 11:19
 **/
@Data
public class ObjectDescriptor implements Comparable<ObjectDescriptor> {
    private ObjecId objecId;
    private ObjectFileds objectFileds = new ObjectFileds();

    /**
     * @param name  name
     * @param value value
     * @return void
     * @title addField
     * @description
     * @author BiJi'an
     * @date 2023-03-19 14:36
     */
    public void addField(String name, Object value) {
        this.objectFileds.add(name, value);
    }

    @Override
    public int compareTo(ObjectDescriptor o) {
        return this.objecId.getLevel() - o.objecId.getLevel();
    }

    /**
     * @author BiJi'an
     * @description
     * @date 2023-03-19 10:50
     **/
    @Data
    public static class ObjectFiled {
        private final String name;
        private final Object value;

    }

    @Data
    public static class ObjectFileds {
        private Map<String, ObjectFiled> datas = MapUtils.newHashMap();

        /**
         * @param name  name
         * @param value value
         * @return void
         * @title add
         * @description
         * @author BiJi'an
         * @date 2023-03-19 15:07
         */
        public void add(String name, Object value) {
            this.datas.put(name, new ObjectFiled(name, value));

        }

    }

}
