package io.github.kylinhunter.commons.clazz.debug;

import io.github.kylinhunter.commons.io.file.UserDirUtils;
import java.io.File;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-05-27 00:22
 */
@Getter
@Setter
public class DebugOption {
  public static final DebugOption INSTANCE = new DebugOption();
  private File saveDir = UserDirUtils.getTmpDir("clazz", "debug");
}
