package io.github.kylinhunter.commons.name;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 14:39
 **/
public interface NameConvertor {
    String convert(String name);

    NCStrategy getNcStrategy();
}
