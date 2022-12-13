package io.github.kylinhunter.commons.uid;

import java.util.StringJoiner;

import io.github.kylinhunter.commons.date.DateUtils;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-13 12:41
 **/
@Data
public class UidInfo {
    private final long sequence;
    private final long type;
    private final long workerId;
    private final long datacenterId;
    private final long timestamp;

    @Override
    public String toString() {
        return new StringJoiner(", ", UidInfo.class.getSimpleName() + "[", "]")
                .add("sequence=" + sequence)
                .add("type=" + type)
                .add("workerId=" + workerId)
                .add("datacenterId=" + datacenterId)
                .add("timestamp=" + timestamp + "/" + DateUtils.format(timestamp))
                .toString();
    }
}
