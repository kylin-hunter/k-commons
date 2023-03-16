package io.github.kylinhunter.commons.yaml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.name.CamelToSnakeUtils;
import io.github.kylinhunter.commons.name.SnakeFormat;
import io.github.kylinhunter.commons.name.SnakeToCamelUtils;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-08 15:20
 **/
@C
@Setter
@RequiredArgsConstructor
public class DefaultLoadCorrector implements LoadCorrector {
    private static final Pattern PATTERN_PROP_NAME = Pattern.compile("^(\\s*-*\\s*)(\\w+-*\\w+)\\s*:");

    public static DefaultLoadCorrector singletion;

    @Override
    public String correct(String text, YamlType yamlType) {
        String[] lines = text.split("(\\r?\\n)+");
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : lines) {
            stringBuilder.append(correntLine(line, yamlType)).append("\n");
        }
        return stringBuilder.toString();

    }

    private String correntLine(String line, YamlType yamlType) {
        Matcher matcher = PATTERN_PROP_NAME.matcher(line);
        if (matcher.find()) {
            if (matcher.groupCount() > 1) {
                String group1 = matcher.group(1);
                String group2 = matcher.group(2);
                if (yamlType == YamlType.CAMLE) {
                    return matcher.replaceAll(group1 + SnakeToCamelUtils.convert(group2) + ":");
                } else if (yamlType == YamlType.SNAKE_UNDERSCORE) {
                    return matcher.replaceAll(
                            group1 + CamelToSnakeUtils.convert(group2, SnakeFormat.LOWWER_UNDERSCORE) + ":");
                } else {
                    return matcher.replaceAll(
                            group1 + CamelToSnakeUtils.convert(group2, SnakeFormat.LOWWER_HYPHEN) + ":");
                }
            }

        }
        return line;
    }

}
