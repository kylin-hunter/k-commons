package io.github.kylinhunter.commons.util.name;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 14:39
 **/
public interface NamingConvertor {
    String convert(String name);

    NCStrategy getNcStrategy();
}
