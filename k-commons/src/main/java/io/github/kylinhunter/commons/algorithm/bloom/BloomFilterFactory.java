package io.github.kylinhunter.commons.algorithm.bloom;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @author BiJi'an
 * @description
 * @date 2022-07-31 19:34
 **/
@SuppressWarnings({"UnstableApiUsage", "unused"})
public class BloomFilterFactory {

    /**
     * @param expectedInsertions expectedInsertions
     * @param fpp                fpp
     * @return com.google.common.hash.BloomFilter<java.lang.Integer>
     * @title createInteger
     * @description
     * @author BiJi'an
     * @date 2022-12-27 23:52
     */
    public static BloomFilter<Integer> createInteger(int expectedInsertions, double fpp) {
        return BloomFilter.create(Funnels.integerFunnel(), expectedInsertions, fpp);
    }

    /**
     * @param expectedInsertions expectedInsertions
     * @param fpp                fpp
     * @return com.google.common.hash.BloomFilter<java.lang.Long>
     * @title createLong
     * @description
     * @author BiJi'an
     * @date 2022-12-27 23:52
     */
    public static BloomFilter<Long> createLong(int expectedInsertions, double fpp) {
        return BloomFilter.create(Funnels.longFunnel(), expectedInsertions, fpp);
    }

    /**
     * @param expectedInsertions expectedInsertions
     * @param fpp                fpp
     * @return com.google.common.hash.BloomFilter<java.lang.CharSequence>
     * @title createString
     * @description
     * @author BiJi'an
     * @date 2022-12-27 23:52
     */
    public static BloomFilter<CharSequence> createString(int expectedInsertions, double fpp) {
        return BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), expectedInsertions, fpp);
    }

    /**
     * @param expectedInsertions expectedInsertions
     * @param fpp                fpp
     * @return com.google.common.hash.BloomFilter<byte [ ]>
     * @title createByteArray
     * @description
     * @author BiJi'an
     * @date 2022-12-27 23:52
     */
    public static BloomFilter<byte[]> createByteArray(int expectedInsertions, double fpp) {
        return BloomFilter.create(Funnels.byteArrayFunnel(), expectedInsertions, fpp);
    }

}
