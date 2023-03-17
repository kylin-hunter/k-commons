package io.github.kylinhunter.commons.bean.info;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 17:24
 **/
@Data
public class People{

    private String propString1 = "1";
    private short propShort11 = 11;
    private Short propShortWarpper12 = 12;
    private int propInt21 = 21;
    private Integer propIntWarpper22 = 22;
    private long propLong31 = 31;
    private Long propLongWrapper32 = 32L;
    private float propFloat41 = 4.1f;
    private Float propFloatWrapper42 = 4.2f;
    private double propDouble51 = 5.1d;
    private Double propDoubleWrapper52 = 5.2d;
    private Boolean propBooleanWrapper61 = true;
    private boolean propBoolean62 = true;
}
