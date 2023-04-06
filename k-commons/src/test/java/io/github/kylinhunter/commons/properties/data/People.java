package io.github.kylinhunter.commons.properties.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 17:24
 **/
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class People {
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
}
