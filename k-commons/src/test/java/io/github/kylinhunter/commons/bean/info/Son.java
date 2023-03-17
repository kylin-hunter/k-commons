package io.github.kylinhunter.commons.bean.info;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 01:31
 **/
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Son extends People {
    private String prop01 = "1";
    private short prop02 = 2;
    private Short prop03 = 3;
    private int prop04 = 4;
    private Integer prop05 = 5;
    private long prop06 = 61;
    private Long prop07 = 7L;
    private float prop08 = 8.1f;
    private Float prop09 = 9.1f;
    private double prop10 = 10.1d;
    private Double prop11 = 11.1d;
    private Boolean prop12 = true;
    private boolean prop13 = true;
    private boolean prop13Son = true;
    private Father father31;
    private Father father32;

}
