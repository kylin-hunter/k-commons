package io.github.kylinhunter.commons.bean;

import java.util.List;

import io.github.kylinhunter.commons.bean.copy.convertor.ClassCopy;
import io.github.kylinhunter.commons.bean.copy.convertor.ConvertType;
import io.github.kylinhunter.commons.bean.copy.convertor.FieldCopy;
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

    @FieldCopy(value = ConvertType.JSON, targets = {Bean2.class})
    private BizBean json;

    @FieldCopy(value = ConvertType.JSON, targets = {Bean2.class})
    private List<BizBean> jsonList;

    @FieldCopy(value = ConvertType.BYTES, targets = {Bean2.class})
    private BizBean bytes;

    @FieldCopy(value = ConvertType.BYTES, targets = {Bean2.class})
    private List<BizBean> bytesList;

    @FieldCopy(value = ConvertType.XML, targets = {Bean2.class})
    private BizBean xml;

    //    @FieldCopy(value = ConvertType.XML, targets = {Bean2.class})
    //    private List<BizBean> xmlList;

}
