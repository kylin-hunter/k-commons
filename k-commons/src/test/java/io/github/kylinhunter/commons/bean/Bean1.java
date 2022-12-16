package io.github.kylinhunter.commons.bean;

import java.util.List;

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
public class Bean1 {
    private String name1;
    private String name2;

    @FieldCopy(value = ConvertType.JSON)
    private BizBean json;

    @FieldCopy(value = ConvertType.JSON)
    private List<BizBean> jsonList;

    @FieldCopy(value = ConvertType.BYTES)
    private BizBean bytes;

    @FieldCopy(value = ConvertType.BYTES)
    private List<BizBean> bytesList;

    @FieldCopy(value = ConvertType.XML)
    private BizBean xml;

    //    @FieldCopy(value = ConvertType.XML)
    //    private List<BizBean> xmlList;

}
