package com.kylinhunter.plat.commons.bean;

import java.util.List;

import com.kylinhunter.plat.commons.bean.copy.convertor.ClassCopy;
import com.kylinhunter.plat.commons.bean.copy.convertor.FieldCopy;
import com.kylinhunter.plat.commons.bean.copy.convertor.TargetType;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2022-05-21 17:45
 **/
@Getter
@Setter
@ClassCopy(value = Bean1ClassConvertor.class, targets = Bean2.class)
public class Bean1 {
    private String name1;
    private String name2;

    @FieldCopy(value = TargetType.JSON, targets = {Bean2.class})
    private BeanJson json;

    @FieldCopy(value = TargetType.JSON, targets = {Bean2.class})
    private List<BeanJson> jsons;

}
