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

    private final long sequenceMask;
    private final int workerIdShift;  // work id shift , default: 12bit
    private final int datacenterIdShift; // datacenter id shift ,default: 12+5=17bit
    private final int timestampShift; // time stamp shift ,default: 12+10 = 22bit

    @Getter
    @Setter
    private long lastTimestamp = -1L; // last time stamp

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
        if (workerIdBits + datacenterIdBits + sequenceBits > 22) {
            throw new GeneralException("workerIdBits + datacenterIdBits + sequenceBits must <=22");

        }
        long maxWorkerId = ~(-1L << workerIdBits);  // max worker id
        long maxDatacenterId = ~(-1L << datacenterIdBits); //max datacenter id
        sequenceMask = ~(-1L << sequenceBits);

        workerIdShift = sequenceBits;  // work id shift 12bit
        datacenterIdShift = sequenceBits + workerIdBits; // datacenter id shift 12+5=17bit
        timestampShift = sequenceBits + workerIdBits + datacenterIdBits; // time stamp shift 12+10 = 22bit
        long timestampBits = 63 - sequenceBits - workerIdBits - datacenterIdBits;
        log.info("timestampBits {}, datacenterIdBits {}, workerIdBits {}, sequenceBits {}",
                timestampBits, datacenterIdBits, workerIdBits, sequenceBits);
        if (workerId > maxWorkerId || workerId < 0) {
            throw new ParamException("worker Id can't less than 0 or great than " + maxWorkerId);
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new ParamException("datacenterId Id can't less than 0 or great than " + maxDatacenterId);
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
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = nextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;

        return ((timestamp - INIT_EPOCH) << timestampShift) | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;
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

}
