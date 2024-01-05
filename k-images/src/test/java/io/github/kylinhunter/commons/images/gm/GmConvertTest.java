package io.github.kylinhunter.commons.images.gm;

import java.util.List;
import org.junit.jupiter.api.Test;

class GmConvertTest {

  @Test
  void test() {

    GmConvert cmd = new GmConvert();
    cmd.size(100, 100);
    cmd.resize(100, 100);

    cmd.depth(8);

    List<String> args = cmd.getCmdline();
    args.forEach(System.out::println);
  }

}