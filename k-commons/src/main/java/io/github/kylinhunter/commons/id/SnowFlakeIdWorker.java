package io.github.kylinhunter.commons.id;

import io.github.kylinhunter.commons.exception.embed.GeneralException;
import io.github.kylinhunter.commons.exception.embed.ParamException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SnowFlakeIdWorker {

    private static final long INIT_EPOCH = 1632499200000L; // initialized time stamp

    @Getter
    @Setter
    private long workerId; // worker id

    @Getter
    @Setter
    private long datacenterId; //  datacenter id

    @Getter
    @Setter
    private long sequence; //  sequence

    @Getter
    @Setter
    private long lastTimestamp = -1L; // last time stamp

    private final int WORKER_ID_SHIFT;  // work id shift , default: 12bit
    private final int DATA_CENTER_ID_SHIFT; // datacenter id shift ,default: 12+5=17bit
    private final int TIMESTAMP_SHIFT; // time stamp shift ,default: 12+10 = 22bit

    private final long SEQUENCE_MAX;
    private final int WORKER_ID_MAX;
    private final int DATA_CENTER_ID_MAX;
    private final long TIMESTAMP_MAX;

    public SnowFlakeIdWorker(long workerId, long datacenterId) {
        this(12, 5, 5, workerId, datacenterId);

    }

    public SnowFlakeIdWorker(int sequenceBits, int workerIdBits, int datacenterIdBits, long workerId,
                             long datacenterId) {
        if (workerIdBits < 2 || workerIdBits > 8) {
            throw new GeneralException("workerIdBits must between [2-5]");
        }
        if (datacenterIdBits < 2 || datacenterIdBits > 8) {
            throw new GeneralException("datacenterIdBits must between [2-5]");
        }

        if (sequenceBits < 10 || sequenceBits > 18) {
            throw new GeneralException("sequenceBits must between [5-20]");
        }
        int bits = workerIdBits + datacenterIdBits + sequenceBits;
        if (bits < 18 || bits > 26) {
            throw new GeneralException("workerIdBits + datacenterIdBits + sequenceBits between [18-26]");

        }
        this.WORKER_ID_MAX = ~(-1 << workerIdBits);  // max worker id
        this.DATA_CENTER_ID_MAX = ~(-1 << datacenterIdBits); //max datacenter id
        SEQUENCE_MAX = ~(-1L << sequenceBits);

        WORKER_ID_SHIFT = sequenceBits;  // work id shift 12bit
        DATA_CENTER_ID_SHIFT = sequenceBits + workerIdBits; // datacenter id shift 12+5=17bit
        TIMESTAMP_SHIFT = sequenceBits + workerIdBits + datacenterIdBits; // time stamp shift 12+10 = 22bit
        long timestampBits = 63 - sequenceBits - workerIdBits - datacenterIdBits;
        log.info("timestampBits {}, datacenterIdBits {}, workerIdBits {}, sequenceBits {}",
                timestampBits, datacenterIdBits, workerIdBits, sequenceBits);
        TIMESTAMP_MAX = ~(-1L << timestampBits);

        if (workerId > WORKER_ID_MAX || workerId < 0) {
            throw new ParamException("worker Id can't less than 0 or great than " + WORKER_ID_MAX);
        }
        if (datacenterId > DATA_CENTER_ID_MAX || datacenterId < 0) {
            throw new ParamException("datacenterId Id can't less than 0 or great than " + DATA_CENTER_ID_MAX);
        }

        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * @return long
     * @title nextId
     * @description
     * @author BiJi'an
     * @date 2022-12-13 00:39
     */
    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new GeneralException("clock is moving backwards.  Rejecting requests until " + lastTimestamp);
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MAX;
            if (sequence == 0) {
                timestamp = nextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;

        return ((timestamp - INIT_EPOCH) << TIMESTAMP_SHIFT) | (datacenterId << DATA_CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT) | sequence;
    }

    /**
     * @param lastTimestamp lastTimestamp
     * @return long
     * @title tilNextMillis
     * @description
     * @author BiJi'an
     * @date 2022-12-13 00:48
     */
    private long nextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    public SnowFlakerInfo parse(long id) {
        long sequence = id & SEQUENCE_MAX;
        long workerId = id >> WORKER_ID_SHIFT & WORKER_ID_MAX;
        long datacenterId = id >> DATA_CENTER_ID_SHIFT & DATA_CENTER_ID_MAX;
        long timestamp = (id >> TIMESTAMP_SHIFT & TIMESTAMP_MAX) + INIT_EPOCH;
        return new SnowFlakerInfo(sequence, workerId, datacenterId, timestamp);
    }

}
