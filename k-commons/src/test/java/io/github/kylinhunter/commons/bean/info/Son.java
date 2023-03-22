package io.github.kylinhunter.commons.bean.info;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 01:31
 **/
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(callSuper = true)
public class Son extends People {
    @EqualsAndHashCode.Include
    private String p01;
    @EqualsAndHashCode.Include
    private short p02;
    @EqualsAndHashCode.Include
    private Short p03;
    @EqualsAndHashCode.Include
    private int p04;
    @EqualsAndHashCode.Include
    private Integer p05;
    @EqualsAndHashCode.Include
    private long p06;
    @EqualsAndHashCode.Include
    private Long p07;
    @EqualsAndHashCode.Include
    private float p08;
    @EqualsAndHashCode.Include
    private Float p09;
    @EqualsAndHashCode.Include
    private double p10;
    @EqualsAndHashCode.Include
    private Double p11;
    @EqualsAndHashCode.Include
    private Boolean p12;
    @EqualsAndHashCode.Include
    private boolean p13;
    @EqualsAndHashCode.Include
    private String specialSon;

    @EqualsAndHashCode.Include
    private Father father5;
    @EqualsAndHashCode.Include
    private Father father6;

}
