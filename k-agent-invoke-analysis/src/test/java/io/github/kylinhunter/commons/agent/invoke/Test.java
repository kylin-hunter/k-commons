package io.github.kylinhunter.commons.agent.invoke;

import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.io.ResourceHelper.PathType;

/**
 * @author BiJi'an
 * @description
 * @date 2023-06-25 22:44
 */
public class Test {

  public static void main(String[] args) {
    ResourceHelper.getDir(
        "/Users/bijian/Documents/workspace_gitee/k-commons/k-agent-invoke-analysis/tmp",
        PathType.FILESYSTEM, true);
  }

}