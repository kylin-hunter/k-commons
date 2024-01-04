package io.github.kylinhunter.commons.images.gm;

import io.github.kylinhunter.commons.images.gm.arg.ArgContext;
import java.util.List;
import org.junit.jupiter.api.Test;

class ConvertCmdTest {

  @Test
  void test() {

    ConvertCmd cmd = new ConvertCmd();
    cmd.resize(10, 20);
    cmd.depth(10);
    ArgContext argContext = cmd.argContext;

    List<String> args = cmd.getArgs();
    args.forEach(System.out::println);
  }

}