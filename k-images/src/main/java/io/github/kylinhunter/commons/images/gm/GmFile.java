package io.github.kylinhunter.commons.images.gm;

import io.github.kylinhunter.commons.io.file.FileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 17:25
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GmFile {

  @Include
  private final File file;

  private final Type type;
  private final OutputStream out; // for the image to be written

  public void delete() {
    FileUtil.deleteQuietly(file);
  }

  public InputStream getInputStream() throws FileNotFoundException {
    return new FileInputStream(file);
  }

  public static enum Type {
    INPUT, OUTPUT;
  }

}