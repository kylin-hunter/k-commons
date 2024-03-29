package io.github.kylinhunter.commons.agent.invoke.test.bean;

import java.util.UUID;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 16:28
 */
@Data
public class Result {

  private String result1;
  private String result2;
  private Param param;

  public Result(Param param) {
    this.param = param;
    this.result1 = param.getParam1() + "/" + UUID.randomUUID().toString();
    this.result2 = param.getParam2() + "/" + UUID.randomUUID().toString();
  }
}
