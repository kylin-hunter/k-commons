package com.kylinhunter.user;
import io.github.kylinhunter.commons.io.file.FileUtil;
import java.io.Serializable;
import java.time.LocalTime;
import java.sql.Timestamp;
import java.sql.Date;
import java.sql.Time;
import java.math.BigDecimal;
/**
 * @description  the user
 * @author bijian
 * @since 2023-12-21
 * basic_vm_custom_property_value
 **/
public class UserBasic extends FileUtil implements Serializable,Cloneable{
    private Long id; // primary unique id
    private String name; // user name
    private LocalTime birth; // birthday
    private Timestamp leaveCompanyTime; // the time leave company
    private Date joinCompanyDate; // what time to join the company
    private Time workTime; // what time to work ervery moring
    private Integer workHours; // how many hours to work everyday
    private Short age; // age
    private Float height; // height
    private Double weight; // weight
    private BigDecimal moneyIncome; // all money income
    private Long moneySpend; // the money spent
    private String address; // address
    private Boolean deleteFlag; // is deleted
    private Integer sex; // 0 unkown 1 male 2 female
    private Integer roleId; // 角色 ID

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

