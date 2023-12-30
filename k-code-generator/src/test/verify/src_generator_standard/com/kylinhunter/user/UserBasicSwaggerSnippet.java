package com.kylinhunter.user;
import io.github.kylinhunter.commons.io.file.FileUtil;
import java.io.Serializable;
import java.time.LocalTime;
import java.sql.Timestamp;
import java.sql.Date;
import java.sql.Time;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description  the user
 * @author bijian
 * @since 2023-12-21
 * basic_swagger_snippet_vm_custom_property_value
 **/
@ApiModel(value="UserBasicSwaggerSnippet", description="the user")
public class UserBasicSwaggerSnippet extends FileUtil implements Serializable,Cloneable{
    @ApiModelProperty(value = "primary unique id")
    private Long id;
    @ApiModelProperty(value = "user name")
    private String name;
    @ApiModelProperty(value = "birthday")
    private LocalTime birth;
    @ApiModelProperty(value = "the time leave company")
    private Timestamp leaveCompanyTime;
    @ApiModelProperty(value = "what time to join the company")
    private Date joinCompanyDate;
    @ApiModelProperty(value = "what time to work ervery moring")
    private Time workTime;
    @ApiModelProperty(value = "how many hours to work everyday")
    private Integer workHours;
    @ApiModelProperty(value = "age")
    private Short age;
    @ApiModelProperty(value = "height")
    private Float height;
    @ApiModelProperty(value = "weight")
    private Double weight;
    @ApiModelProperty(value = "all money income")
    private BigDecimal moneyIncome;
    @ApiModelProperty(value = "the money spent")
    private Long moneySpend;
    @ApiModelProperty(value = "address")
    private String address;
    @ApiModelProperty(value = "is deleted")
    private Boolean deleteFlag;
    @ApiModelProperty(value = "0 unkown 1 male 2 female")
    private Integer sex;
    @ApiModelProperty(value = "角色 ID")
    private Integer roleId;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
