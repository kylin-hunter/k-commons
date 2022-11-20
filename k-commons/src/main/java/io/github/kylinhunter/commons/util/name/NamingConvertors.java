package io.github.kylinhunter.commons.util.name;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.kylinhunter.commons.component.C;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 15:07
 **/
@C
@RequiredArgsConstructor
public class NamingConvertors {
    private static Map<NCStrategy, NamingConvertor> namingConvertorMap = null;

    public NamingConvertors(List<NamingConvertor> namingConvertors) {
        namingConvertorMap = namingConvertors.stream().collect(Collectors.toMap(e -> e.getNcStrategy(), e -> e));
    }

    public static String convert(NCStrategy ncStrategy, String name) {
        return namingConvertorMap.get(ncStrategy).convert(name);
    }
}
