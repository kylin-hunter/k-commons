package io.github.kylinhunter.commons.bean.info;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 01:31
 **/
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SimpleSon {
    @EqualsAndHashCode.Include
    private String name;
    @EqualsAndHashCode.Include
    private SimpleGrandSon[] grandSonArr;
    @EqualsAndHashCode.Include
    private List<SimpleGrandSon> grandSonList;

}
