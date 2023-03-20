package io.github.kylinhunter.commons.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 14:25
 **/
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ObjecId implements Comparable<ObjecId> {
    @EqualsAndHashCode.Include
    private String id;
    private int level;
    private String parentId;
    private Class<?> type;

    public ObjecId(String propName) {

        int i = propName.lastIndexOf(".");
        if (i > 0) {
            id = propName.substring(0, i);
            this.level = id.split("\\.").length + 1;
            final int i1 = id.indexOf(".");
            if (i1 > 0) {
                this.parentId = id.substring(0, i1);

            } else {
                this.parentId = "#";
            }

        } else {
            this.id = "#";
            this.level = 1;

        }
    }

    @Override
    public int compareTo(ObjecId o) {
        return this.level - o.level;
    }
}
