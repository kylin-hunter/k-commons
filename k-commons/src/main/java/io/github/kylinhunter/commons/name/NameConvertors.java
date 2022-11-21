package io.github.kylinhunter.commons.name;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.kylinhunter.commons.component.C;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 15:07
 **/
@C
public class NameConvertors {
    private final Map<NCStrategy, NameConvertor> namingConvertorMap;
    public NameConvertors(List<NameConvertor> nameConvertors) {
        namingConvertorMap = nameConvertors.stream().collect(Collectors.toMap(NameConvertor::getNcStrategy, e -> e));
    }

    public String convert(NCStrategy ncStrategy, String name) {
        return namingConvertorMap.get(ncStrategy).convert(name);
    }
}
