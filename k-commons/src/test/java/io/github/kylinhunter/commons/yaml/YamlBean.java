package io.github.kylinhunter.commons.yaml;

import java.util.List;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-02 20:28
 **/
@Data
public class YamlBean implements Cloneable {
    private int id;
    private String name;
    List<String> values;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
