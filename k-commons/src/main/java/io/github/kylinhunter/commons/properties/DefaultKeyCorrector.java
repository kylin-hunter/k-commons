package io.github.kylinhunter.commons.properties;

import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.name.NameRule;
import io.github.kylinhunter.commons.name.NameUtils;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-08 15:20
 **/
@Setter
@RequiredArgsConstructor
public class DefaultKeyCorrector implements KeyCorrector {

    @Override
    public Object correct(Object key, NameRule nameRule) {
        if (key instanceof String) {
            String newKey = (String) key;
            String[] keyPath = StringUtils.split(newKey, ".");
            if (keyPath.length <= 1) {
                newKey = NameUtils.convert(newKey, nameRule);
            } else {
                StringJoiner stringJoiner = new StringJoiner(".");
                for (String s : keyPath) {
                    stringJoiner.add(NameUtils.convert(s, nameRule));

                }
                newKey = stringJoiner.toString();
            }
            return newKey;
        }
        return key;
    }
}
