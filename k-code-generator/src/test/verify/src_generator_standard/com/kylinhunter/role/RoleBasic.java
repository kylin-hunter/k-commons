package com.kylinhunter.role;
import io.github.kylinhunter.commons.io.file.FileUtil;
import java.io.Serializable;
/**
 * @description  the roles
 * @author bijian
 * @since 2023-12-21
 * basic_vm_custom_property_value
 **/
public class RoleBasic extends FileUtil implements Serializable,Cloneable{
    private Long id; // primary unique id
    private String name; // role name

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

