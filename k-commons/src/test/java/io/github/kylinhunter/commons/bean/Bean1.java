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


    @FieldCopy(value = ConvertType.NUM_2_STR)
    private int intValue1;
    @FieldCopy(value = ConvertType.NUM_2_STR)
    private Integer intValue2;
    @FieldCopy(value = ConvertType.NUM_2_STR)
    private Integer intValue3;

    @FieldCopy(value = ConvertType.NUM_2_STR)
    private long longValue1;
    @FieldCopy(value = ConvertType.NUM_2_STR)
    private Long longValue2;
    @FieldCopy(value = ConvertType.NUM_2_STR)
    private Long longValue3;

    @FieldCopy(value = ConvertType.NUM_2_STR)
    private float floatValue1;
    @FieldCopy(value = ConvertType.NUM_2_STR)
    private Float floatValue2;
    @FieldCopy(value = ConvertType.NUM_2_STR)
    private Float floatValue3;


    @FieldCopy(value = ConvertType.NUM_2_STR)
    private double doubleValue1;
    @FieldCopy(value = ConvertType.NUM_2_STR)
    private Double doubleValue2;
    @FieldCopy(value = ConvertType.NUM_2_STR)
    private Double doubleValue3;

    @FieldCopy(value = ConvertType.OBJ_2_JSON)
    private BizBean json;

    @FieldCopy(value = ConvertType.OBJ_2_JSON)
    private List<BizBean> jsonList;

    @FieldCopy(value = ConvertType.OBJ_2_BYTES)
    private BizBean bytes;

    @FieldCopy(value = ConvertType.OBJ_2_BYTES)
    private List<BizBean> bytesList;

    @FieldCopy(value = ConvertType.OBJ_2_XML)
    private BizBean xml;

    //    @FieldCopy(value = ConvertType.OBJ_2_XML)
    //    private List<BizBean> xmlList;

}
