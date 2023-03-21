package io.github.kylinhunter.commons.properties;

import java.util.Map;
import java.util.function.BiConsumer;

import io.github.kylinhunter.commons.collections.MapUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 11:19
 **/
@Data
public class ObjectDescriptor implements Comparable<ObjectDescriptor> {
    private final ObjecId objecId;
    private ObjectFileds fields = new ObjectFileds();

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
        this.fields.add(name, value);
    }

    /**
     * @see Comparable#compareTo
     */
    @Override
    public int compareTo(ObjectDescriptor o) {
        return this.objecId.level - o.objecId.level;
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

    /**
     * @author BiJi'an
     * @description
     * @date 2023-03-19 10:50
     **/
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

        /**
         * @param action action
         * @return void
         * @title forEach
         * @description
         * @author BiJi'an
         * @date 2023-03-21 01:29
         */
        public void forEach(BiConsumer<String, ObjectFiled> action) {
            datas.forEach(action);
        }
    }

    /**
     * @author BiJi'an
     * @description
     * @date 2023-03-19 14:25
     **/
    @Data
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    public static class ObjecId {
        public static final String ROOT_ID = "#";

        @EqualsAndHashCode.Include
        protected String id;
        protected String parentId;
        protected int level;

        public ObjecId(String propName) {
            int index = propName.lastIndexOf(".");
            if (index > 0) {
                this.id = propName.substring(0, index);
                this.level = id.split("\\.").length + 1;
                int parentIdIndex = this.id.lastIndexOf(".");
                if (parentIdIndex > 0) {
                    this.parentId = this.id.substring(0, parentIdIndex);
                } else {
                    this.parentId = ROOT_ID;
                }
            } else {
                this.id = ROOT_ID;
                this.level = 1;
            }
        }

    }
}
