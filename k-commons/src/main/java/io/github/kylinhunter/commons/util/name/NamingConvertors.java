package io.github.kylinhunter.commons.util.name;

import io.github.kylinhunter.commons.service.DefaultEService;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 15:07
 **/
public class NamingConvertors extends DefaultEService<NCStrategy, NamingConvertor> {
    private static final NamingConvertors singleton = new NamingConvertors();

    public static String convert(NCStrategy ncStrategy, String name) {
        return singleton.get(ncStrategy).convert(name);
    }
}
