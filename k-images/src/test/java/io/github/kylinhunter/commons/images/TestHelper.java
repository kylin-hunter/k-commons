package io.github.kylinhunter.commons.images;

import io.github.kylinhunter.commons.cmd.ExecResult;
import io.github.kylinhunter.commons.images.gm.Cmd;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.io.file.TmpDirUtils;
import java.io.File;
import org.junit.jupiter.api.Assertions;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 10:11
 */
public class TestHelper {

  public static final String JPG = "jpg.jpg";
  public static final String PNG = "png.png";

  /**
   * @return java.io.File
   * @title getJpg
   * @description getJpg
   * @author BiJi'an
   * @date 2024-01-01 10:15
   */
  public static File getJpgFile() {
    return ResourceHelper.getFileInClassPath("/jpg.jpg");
  }

  /**
   * @return java.io.File
   * @title getPng
   * @description getPng
   * @author BiJi'an
   * @date 2024-01-01 10:15
   */
  public static File getPngFile() {
    return ResourceHelper.getFileInClassPath("/png.png");

  }


  public static void run(Cmd cmd) {
    System.out.println(cmd.command());
    ExecResult execResult = cmd.run();
    System.out.println(execResult);
    Assertions.assertTrue(execResult.isSuccess());
  }

  public static File getTmp(String fileName) {
    return TmpDirUtils.getUserFile("k-images", fileName);

  }
}