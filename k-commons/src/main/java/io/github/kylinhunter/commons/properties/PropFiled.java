package io.github.kylinhunter.commons.properties;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 10:50
 **/
@Data
public class PropFiled {
    private String objecId;
    private String name;
    private String fullName;
    private Object value;
    private boolean bean;

    public PropFiled(String fullName) {
        this(fullName, true, null);
    }

    public PropFiled(String fullName, Object value) {
        this(fullName, false, value);
    }

    /**
     * @param fullName fullName
     * @param bean     bean
     * @param value    value
     * @return
     * @title PropFiled
     * @description
     * @author BiJi'an
     * @date 2023-03-31 01:05
     */
    private PropFiled(String fullName, boolean bean, Object value) {
        this.fullName = fullName;
        this.bean = bean;
        this.value = value;

        int index = fullName.lastIndexOf(".");
        if (index > 0) {
            this.objecId = fullName.substring(0, index);

        } else {
            this.objecId = PropObject.ROOT_ID;
        }

        index = fullName.lastIndexOf(".");
        if (index > 0) {
            this.name = fullName.substring(index + 1);
        } else {
            this.name = fullName;
        }
        index = this.name.indexOf("[");
        if (index > 0) {
            this.name = this.name.substring(0, index);
        }
    }

}
