package io.github.kylinhunter.commons.utils.algorithm.bloom;

import com.google.common.hash.BloomFilter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class BloomFilterFactoryTest {

  @SuppressWarnings("UnstableApiUsage")
  @Test
  void createInteger() {
    int cap = 100000;
    BloomFilter<Integer> bloomFilter = BloomFilterFactory.createInteger(cap, 0.001);
    System.out.println("###################################################");
    int mightContainNumber0 = 0;

    List<Integer> list = new ArrayList<>(cap);
    for (int i = 0; i < cap; i++) {

      if (bloomFilter.mightContain(i)) {
        mightContainNumber0++;
      }

      bloomFilter.put(i);
      list.add(i);
    }
    int mightContainNumber1 = 0;
    NumberFormat percentFormat = NumberFormat.getPercentInstance();
    percentFormat.setMaximumFractionDigits(2);

    for (Integer key : list) {
      if (bloomFilter.mightContain(key)) {
        mightContainNumber1++;
      }
    }

    int mightContainNumber2 = 0;
    for (int i = 0; i < cap; i++) {
      if (bloomFilter.mightContain(i + cap)) {
        mightContainNumber2++;
      }
    }
    System.out.println("cap：" + cap);

    System.out.println("conflict num：" + mightContainNumber0);
    System.out.println("conflict rate：" + percentFormat.format((float) mightContainNumber0 / cap));

    System.out.println("correct num：" + mightContainNumber1);
    System.out.println(
        "correct recognition rate：" + percentFormat.format((float) mightContainNumber1 / cap));

    System.out.println("error num：" + mightContainNumber2);
    System.out.println(
        "error recognition rate：" + percentFormat.format((float) mightContainNumber2 / cap));

    System.out.println("###################################################");
  }

  @Test
  void createString() {
    int cap = 100000;
    BloomFilter<CharSequence> bloomFilter = BloomFilterFactory.createString(cap, 0.001);
    System.out.println("###################################################");
    int mightContainNumber0 = 0;

    List<Long> list = new ArrayList<>(cap);
    for (long i = 0; i < cap; i++) {

      if (bloomFilter.mightContain(String.valueOf(i))) {
        mightContainNumber0++;
      }

      bloomFilter.put(String.valueOf(i));
      list.add(i);
    }
    int mightContainNumber1 = 0;
    NumberFormat percentFormat = NumberFormat.getPercentInstance();
    percentFormat.setMaximumFractionDigits(2);

    for (Long key : list) {
      if (bloomFilter.mightContain(String.valueOf(key))) {
        mightContainNumber1++;
      }
    }

    int mightContainNumber2 = 0;
    for (long i = 0; i < cap; i++) {
      if (bloomFilter.mightContain(String.valueOf(i + cap))) {
        mightContainNumber2++;
      }
    }
    System.out.println("cap：" + cap);

    System.out.println("conflict num：" + mightContainNumber0);
    System.out.println("conflict rate：" + percentFormat.format((float) mightContainNumber0 / cap));

    System.out.println("correct num：" + mightContainNumber1);
    System.out.println(
        "correct recognition rate：" + percentFormat.format((float) mightContainNumber1 / cap));

    System.out.println("error num：" + mightContainNumber2);
    System.out.println(
        "error recognition rate：" + percentFormat.format((float) mightContainNumber2 / cap));

    System.out.println("###################################################");
  }

  @Test
  void createLong() {
    int cap = 100000;
    BloomFilter<Long> bloomFilter = BloomFilterFactory.createLong(cap, 0.001);
    System.out.println("###################################################");
    int mightContainNumber0 = 0;

    List<Long> list = new ArrayList<>(cap);
    for (long i = 0; i < cap; i++) {

      if (bloomFilter.mightContain(i)) {
        mightContainNumber0++;
      }

      bloomFilter.put(i);
      list.add(i);
    }
    int mightContainNumber1 = 0;
    NumberFormat percentFormat = NumberFormat.getPercentInstance();
    percentFormat.setMaximumFractionDigits(2);

    for (Long key : list) {
      if (bloomFilter.mightContain(key)) {
        mightContainNumber1++;
      }
    }

    int mightContainNumber2 = 0;
    for (long i = 0; i < cap; i++) {
      if (bloomFilter.mightContain(i + cap)) {
        mightContainNumber2++;
      }
    }
    System.out.println("cap：" + cap);

    System.out.println("conflict num：" + mightContainNumber0);
    System.out.println("conflict rate：" + percentFormat.format((float) mightContainNumber0 / cap));

    System.out.println("correct num：" + mightContainNumber1);
    System.out.println(
        "correct recognition rate：" + percentFormat.format((float) mightContainNumber1 / cap));

    System.out.println("error num：" + mightContainNumber2);
    System.out.println(
        "error recognition rate：" + percentFormat.format((float) mightContainNumber2 / cap));

    System.out.println("###################################################");
  }
}
