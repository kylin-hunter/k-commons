package com.kylinhunter.role;
import io.github.kylinhunter.commons.io.file.FileUtil;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description  the roles
 * @author bijian
 * @since 2023-12-21
 * basic_swagger_vm_custom_property_value
 **/
@ApiModel(value="RoleBasicSwagger", description="the roles")
public class RoleBasicSwagger extends FileUtil implements Serializable,Cloneable{
    @ApiModelProperty(value = "primary unique id")
    private Long id;
    @ApiModelProperty(value = "role name")
    private String name;
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
