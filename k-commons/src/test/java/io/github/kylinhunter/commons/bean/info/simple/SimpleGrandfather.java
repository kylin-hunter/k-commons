package io.github.kylinhunter.commons.bean.info.simple;

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
public class SimpleGrandfather {
    @EqualsAndHashCode.Include
    private String name;
    @EqualsAndHashCode.Include
    private SimpleFather father;

    @Override
    public String toString() {
        return new StringJoiner("\n", SimpleGrandfather.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("father=" + father)
                .toString();
    }
}
