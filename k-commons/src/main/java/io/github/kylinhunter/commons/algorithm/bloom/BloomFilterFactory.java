/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.algorithm.bloom;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import java.nio.charset.StandardCharsets;

/**
 * @author BiJi'an
 * @description
 * @date 2022-07-31 19:34
 */
@SuppressWarnings({"UnstableApiUsage", "unused"})
public class BloomFilterFactory {

  /**
   * @param expectedInsertions expectedInsertions
   * @param fpp fpp
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
   * @param fpp fpp
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
   * @param fpp fpp
   * @return com.google.common.hash.BloomFilter<java.lang.CharSequence>
   * @title createString
   * @description
   * @author BiJi'an
   * @date 2022-12-27 23:52
   */
  public static BloomFilter<CharSequence> createString(int expectedInsertions, double fpp) {
    return BloomFilter.create(
        Funnels.stringFunnel(StandardCharsets.UTF_8), expectedInsertions, fpp);
  }

  /**
   * @param expectedInsertions expectedInsertions
   * @param fpp fpp
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
