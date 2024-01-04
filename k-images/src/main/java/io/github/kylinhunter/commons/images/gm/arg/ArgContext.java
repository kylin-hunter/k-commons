package io.github.kylinhunter.commons.images.gm.arg;

import io.github.kylinhunter.commons.collections.ListUtils;
import java.util.List;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 00:08
 */
public class ArgContext {


  @Getter
  private List<String> args = ListUtils.newArrayList();


  public ArgContext add(String arg) {
    args.add(arg);
    return this;
  }


}