package io.github.kylinhunter.commons.bean.info.simple;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @Override
    public String toString() {
        return new StringJoiner("\n ", SimpleSon.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("grandSonArr=" + Arrays.toString(grandSonArr))
                .add("grandSonList=" + grandSonList)
                .toString();
    }
}
