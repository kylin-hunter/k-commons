package io.github.kylinhunter.commons.generator.template.config;

import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.io.file.path.PathUtil;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-06 01:40
 */
@Getter
@Setter
public class OutputConfig {

  private Path outputPath = UserDirUtils.getDir(true, "output").toPath();
  private boolean autoClean = false;
  private boolean autoCreate = true;
  private boolean override = true;
  private Charset defaultEncoding = StandardCharsets.UTF_8;

  /**
   * @param outputPath outputDir
   * @return void
   * @title setOutputDir
   * @description
   * @author BiJi'an
   * @date 2023-01-08 23:08
   */
  public void setOutputPath(Path outputPath) {

    PathUtil.checkDir(outputPath, autoCreate);
    this.outputPath = outputPath;
  }

  /**
   * @param outputPath outputPath
   * @return void
   * @title setOutputPath
   * @description
   * @author BiJi'an
   * @date 2023-02-26 15:20
   */
  public void setOutputPath(String outputPath) {
    File dir = ResourceHelper.getDir(outputPath, ResourceHelper.PathType.FILESYSTEM, true);
    this.setOutputPath(dir.toPath());
  }
}
