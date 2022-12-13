package io.github.kylinhunter.commons.uid;

import io.github.kylinhunter.commons.exception.embed.GeneralException;
import io.github.kylinhunter.commons.exception.embed.ParamException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UidGenerator {

    private static final long INIT_EPOCH = 1632499200000L; // initialized time stamp
    @Getter
    @Setter
    private long sequence; //  sequence

    @Getter
    @Setter
    private long type; //  sequence

    @Getter
    @Setter
    private long workerId; // worker id

    @Getter
    @Setter
    private long datacenterId; //  datacenter id

    @Getter
    @Setter
    private long lastTimestamp = -1L; // last time stamp

    private final int TYPE_SHIFT;  // work id shift , default: 12bit
    private final int WORKER_ID_SHIFT;  // work id shift , default: 12bit
    private final int DATA_CENTER_ID_SHIFT; // datacenter id shift ,default: 12+5=17bit
    private final int TIMESTAMP_SHIFT; // time stamp shift ,default: 12+10 = 22bit

    private final long SEQUENCE_MAX;
    private final long TYPE_MAX;
    private final int WORKER_ID_MAX;
    private final int DATA_CENTER_ID_MAX;
    private final long TIMESTAMP_MAX;

    /**
     * @param type         type
     * @param workerId     workerId
     * @param datacenterId datacenterId
     * @title UidGenerator
     * @description
     * @author BiJi'an
     * @date 2022-12-11 16:19
     */
    public UidGenerator(long type, long workerId, long datacenterId) {
        this(12, 4, 4, 2, type, workerId, datacenterId);

    }

    /**
     * @param sequenceBits     sequenceBits
     * @param typeBits         typeBits
     * @param workerIdBits     workerIdBits
     * @param datacenterIdBits datacenterIdBits
     * @param type             type
     * @param workerId         workerId
     * @param datacenterId     datacenterId
     * @title UidGenerator
     * @description
     * @author BiJi'an
     * @date 2022-12-11 16:20
     */
    public UidGenerator(int sequenceBits, int typeBits, int workerIdBits, int datacenterIdBits,
                        long type, long workerId,
                        long datacenterId) {
        if (sequenceBits < 10 || sequenceBits > 16) {
            throw new GeneralException("sequenceBits must between [5-20]");
        }

        if (typeBits < 1 || typeBits > 8) {
            throw new GeneralException("typeBits must between [2-5]");
        }

        if (workerIdBits < 2 || workerIdBits > 8) {
            throw new GeneralException("workerIdBits must between [2-5]");
        }
        if (datacenterIdBits < 1 || datacenterIdBits > 5) {
            throw new GeneralException("datacenterIdBits must between [2-5]");
        }

        int bits = workerIdBits + datacenterIdBits + sequenceBits;
        if (bits < 18 || bits > 26) {
            throw new GeneralException("workerIdBits + datacenterIdBits + sequenceBits between [18-26]");

        }
        this.SEQUENCE_MAX = ~(-1L << sequenceBits);
        this.TYPE_MAX = ~(-1 << typeBits);  // max worker id
        this.WORKER_ID_MAX = ~(-1 << workerIdBits);  // max worker id
        this.DATA_CENTER_ID_MAX = ~(-1 << datacenterIdBits); //max datacenter id

        TYPE_SHIFT = sequenceBits;  // work id shift 12bit

        WORKER_ID_SHIFT = sequenceBits + typeBits;  // work id shift 12bit
        DATA_CENTER_ID_SHIFT = sequenceBits + typeBits + workerIdBits; // datacenter id shift 12+5=17bit
        TIMESTAMP_SHIFT = sequenceBits + typeBits + workerIdBits + datacenterIdBits; // time stamp shift 12+10 = 22bit
        long timestampBits = 63 - sequenceBits - typeBits - workerIdBits - datacenterIdBits;
        TIMESTAMP_MAX = ~(-1L << timestampBits);

        log.info("timestampBits {}, datacenterIdBits {}, workerIdBits {},typeBits {}, sequenceBits {}",
                timestampBits, datacenterIdBits, workerIdBits, typeBits, sequenceBits);

        if (type > TYPE_MAX || type < 0) {
            throw new ParamException("type Id can't less than 0 or great than " + TYPE_MAX);
        }
        if (workerId > WORKER_ID_MAX || workerId < 0) {
            throw new ParamException("worker Id can't less than 0 or great than " + WORKER_ID_MAX);
        }
        if (datacenterId > DATA_CENTER_ID_MAX || datacenterId < 0) {
            throw new ParamException("datacenterId Id can't less than 0 or great than " + DATA_CENTER_ID_MAX);
        }

        this.type = type;
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

        //        log.info("{},{},{},{},{}", sequence, type, workerId, datacenterId, DateUtils.format(timestamp));
        return ((timestamp - INIT_EPOCH) << TIMESTAMP_SHIFT) | (datacenterId << DATA_CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT) | (type << TYPE_SHIFT) | sequence;
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

    /**
     * @param uid uid
     * @return io.github.kylinhunter.commons.uid.UidInfo
     * @title parse
     * @description
     * @author BiJi'an
     * @date 2022-12-11 16:30
     */
    public UidInfo parse(long uid) {
        long sequence = uid & SEQUENCE_MAX;
        long type = uid >> TYPE_SHIFT & TYPE_MAX;
        long workerId = uid >> WORKER_ID_SHIFT & WORKER_ID_MAX;
        long datacenterId = uid >> DATA_CENTER_ID_SHIFT & DATA_CENTER_ID_MAX;
        long timestamp = (uid >> TIMESTAMP_SHIFT & TIMESTAMP_MAX) + INIT_EPOCH;
        return new UidInfo(sequence, type, workerId, datacenterId, timestamp);
    }

}
