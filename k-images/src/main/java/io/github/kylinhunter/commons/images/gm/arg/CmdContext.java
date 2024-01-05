package io.github.kylinhunter.commons.images.gm.arg;

import io.github.kylinhunter.commons.collections.ListUtils;
import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 00:08
 */
public class CmdContext {

  @Getter
  private List<String> cmdline = ListUtils.newArrayList();
  private static final String ARG_SEPARATOR = " ";

  private List<File> tmpFiles = ListUtils.newArrayList();
  private static final String TMP_PREFIX = "tmp";

  private static final String ARG_PREFIX = "-";

  /**
   * @param name name
   * @return io.github.kylinhunter.commons.images.gm.arg.CmdContext
   * @title add arg name to context
   * @description addName
   * @author BiJi'an
   * @date 2024-01-05 11:08
   */
  @SuppressWarnings("UnusedReturnValue")
  public CmdContext addArg(String name, String... values) {
    cmdline.add(ARG_PREFIX + name);
    Collections.addAll(cmdline, values);
    return this;
  }

  /**
   * @param file file
   * @return io.github.kylinhunter.commons.images.gm.arg.CmdContext
   * @title addImage
   * @description addImage
   * @author BiJi'an
   * @date 2024-01-05 15:11
   */
  public CmdContext addImage(File file) {
    cmdline.add(file.getAbsolutePath());
    return this;
  }

  /**
   * @return io.github.kylinhunter.commons.images.gm.arg.CmdContext
   * @title addImage
   * @description addImage
   * @author BiJi'an
   * @date 2024-01-05 15:12
   */

  public CmdContext addImage(InputStream inputStream) {
//    File tmpFiles = UserDirUtils
//    IOUtils.copy(inputStream, tmpFiles);

    return this;
  }


  /**
   * @param cmd cmd
   * @return io.github.kylinhunter.commons.images.gm.arg.CmdContext
   * @title setCmd
   * @description setCmd
   * @author BiJi'an
   * @date 2024-01-05 14:01
   */

  @SuppressWarnings("UnusedReturnValue")
  public CmdContext setCmd(String... cmd) {
    for (int i = 0; i < cmd.length; i++) {
      cmdline.add(i, cmd[i]);
    }
    return this;
  }
}


