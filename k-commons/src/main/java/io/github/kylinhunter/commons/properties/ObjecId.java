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
public class ObjecId {
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
