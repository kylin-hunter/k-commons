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

    @FieldCopy(ConvertType.NUM_STR)
    private int intValue1;
    @FieldCopy(ConvertType.NUM_STR)
    private Integer intValue2;
    @FieldCopy(ConvertType.NUM_STR)
    private Integer intValue3;

    @FieldCopy(ConvertType.NUM_STR)
    private long longValue1;
    @FieldCopy(ConvertType.NUM_STR)
    private Long longValue2;
    @FieldCopy(ConvertType.NUM_STR)
    private Long longValue3;

    @FieldCopy(ConvertType.NUM_STR)
    private float floatValue1;
    @FieldCopy(ConvertType.NUM_STR)
    private Float floatValue2;
    @FieldCopy(ConvertType.NUM_STR)
    private Float floatValue3;

    @FieldCopy(ConvertType.NUM_STR)
    private double doubleValue1;
    @FieldCopy(ConvertType.NUM_STR)
    private Double doubleValue2;
    @FieldCopy(ConvertType.NUM_STR)
    private Double doubleValue3;

    @FieldCopy(ConvertType.JSON)
    private SubBean json;

    @FieldCopy(ConvertType.JSON)
    private List<SubBean> jsonList;

    @FieldCopy(ConvertType.BYTES)
    private SubBean bytes;

    @FieldCopy(ConvertType.BYTES)
    private List<SubBean> bytesList;

    @FieldCopy(ConvertType.XML)
    private SubBean xml;

    @FieldCopy(ConvertType.YAML)
    private SubBean yaml;

}
