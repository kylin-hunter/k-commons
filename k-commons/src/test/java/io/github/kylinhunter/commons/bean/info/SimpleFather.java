package io.github.kylinhunter.commons.bean.info;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class SimpleFather {
    @EqualsAndHashCode.Include
    private String name;

    @EqualsAndHashCode.Include
    private SimpleSon son;

    @EqualsAndHashCode.Include
    private List<SimpleSon> sonList;

    @EqualsAndHashCode.Include
    private List<?> sonList2;

    @EqualsAndHashCode.Include
    private Set<SimpleSon> sonSet;

    @EqualsAndHashCode.Include
    private Map<String, String> sonMap;

    @EqualsAndHashCode.Include
    private SimpleSon[] sonArrays;

}
