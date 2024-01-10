package io.github.kylinhunter.commons.images.gm;

import io.github.kylinhunter.commons.io.file.FileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-10 17:25
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GmFile {

  @Include
  private final File file;
  private final OutputStream out; // for the image to be written

  public void delete() {
    FileUtil.deleteQuietly(file);
  }

  public FileInputStream getFileInputStream() throws FileNotFoundException {
    return new FileInputStream(file);
  }

}