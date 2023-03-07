package io.github.kylinhunter.commons.clazz.monitor;

import java.util.Date;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-07 00:55
 **/
@Data
public class Span {
    private Date enterTime = new Date();
    private final String linkId;
}
