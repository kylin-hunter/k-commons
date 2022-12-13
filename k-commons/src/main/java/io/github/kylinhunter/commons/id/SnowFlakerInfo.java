package io.github.kylinhunter.commons.id;

import java.util.StringJoiner;

import io.github.kylinhunter.commons.date.DateUtils;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-13 12:41
 **/
@Data
public class SnowFlakerInfo {
    private final long sequence;
    private final long workerId;
    private final long datacenterId;
    private final long timestamp;

    @Override
    public String toString() {
        return new StringJoiner(", ", SnowFlakerInfo.class.getSimpleName() + "[", "]")
                .add("sequence=" + sequence)
                .add("workerId=" + workerId)
                .add("datacenterId=" + datacenterId)
                .add("timestamp=" + DateUtils.format(DateUtils.toLocalDateTime(timestamp)))
                .toString();
    }
}
