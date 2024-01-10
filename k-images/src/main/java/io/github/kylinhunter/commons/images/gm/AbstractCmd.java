package io.github.kylinhunter.commons.images.gm;

import io.github.kylinhunter.commons.cmd.CmdExecutor;
import io.github.kylinhunter.commons.cmd.ExecResult;
import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.component.simple.EnumServiceFactory;
import io.github.kylinhunter.commons.exception.check.ThrowChecker;
import io.github.kylinhunter.commons.images.constant.Format;
import io.github.kylinhunter.commons.images.exception.ImageException;
import io.github.kylinhunter.commons.images.gm.arg.Args;
import io.github.kylinhunter.commons.images.gm.arg.CmdContext;
import io.github.kylinhunter.commons.io.IOUtil;
import io.github.kylinhunter.commons.io.file.TmpDirUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 00:08
 */
@Slf4j
public abstract class AbstractCmd {

  protected static EnumServiceFactory argFactory = new EnumServiceFactory();

  static {
    argFactory.init(Args.class);
  }

  protected static CmdExecutor cmdExecutor = new CmdExecutor();

  private List<GmFile> tmpFiles;
  private Map<GmFile, OutputStream> tmpFileOuts;

  protected CmdContext cmdContext = new CmdContext();

  protected AbstractCmd() {
  }


  /**
   * @param args args x   * @title addArg
   * @description addArg
   * @author BiJi'an
   * @date 2024-01-01 15:42
   */

  public void addArg(String... args) {
    cmdContext.addArg(args);
  }


  /**
   * @param file file
   * @title addInputImage
   * @description addInputImage
   * @author BiJi'an
   * @date 2024-01-01 01:17
   */
  public void addImageInput(File file) {

    if (file != null && file.exists() && file.isFile()) {
      cmdContext.addArg(file.getAbsolutePath());
    } else {
      throw new ImageException(
          "addInputImage error" + (file != null ? file.getAbsolutePath() : "null"));
    }

  }

  /**
   * @param path path
   * @title addInputImage
   * @description addInputImage
   * @author BiJi'an
   * @date 2024-01-01 17:05
   */

  public void addImageInput(Path path) {
    File file = path.toFile();
    this.addImageInput(file);
  }

  /**
   * @param file file
   * @title addOutImage
   * @description addOutImage
   * @author BiJi'an
   * @date 2024-01-01 17:01
   */

  public void addImageOut(File file) {
    Objects.requireNonNull(file);
    cmdContext.addArg(file.getAbsolutePath());
  }


  /**
   * @param path path
   * @title addOutImage
   * @description addOutImage
   * @author BiJi'an
   * @date 2024-01-01 10:19
   */

  public void addImageOut(Path path) {
    File file = path.toFile();
    this.addImageOut(file);
  }

  /**
   * @param in     in
   * @param format format
   * @title addImageInput
   * @description addImageInput
   * @author BiJi'an
   * @date 2024-01-01 01:11
   */

  public void addImageInput(InputStream in, Format format) {
    this.addImageInput(in, format.getName());

  }


  /**
   * @param in in
   * @return void
   * @throws
   * @title addImage
   * @description addImage
   * @author BiJi'an
   * @date 2024-01-01 15:45
   */
  public void addImageInput(InputStream in, String format) {
    ThrowChecker.checkNotEmpty(format, "format can't be empty");
    File file = TmpDirUtils.getSysFile(true, "k-image", UUID.randomUUID() + "." + format);
    addTmpFile(file, null);
    try (FileOutputStream outputStream = new FileOutputStream(file)) {
      IOUtil.copy(in, outputStream);
    } catch (Exception e) {
      throw new ImageException("addImage error", e);
    }

    cmdContext.addArg(file.getAbsolutePath());

  }

  /**
   * @param out    out
   * @param format format
   * @title addImageOut
   * @description addImageOut
   * @author BiJi'an
   * @date 2024-01-01 01:12
   */

  public void addImageOut(OutputStream out, Format format) {
    this.addImageOut(out, format.getName());
  }

  /**
   * @param out out
   * @title addImage
   * @description addImage
   * @author BiJi'an
   * @date 2024-01-01 15:59
   */

  public void addImageOut(OutputStream out, String format) {
    ThrowChecker.checkNotEmpty(format, "format can't be empty");
    File file = TmpDirUtils.getSysFile(true, "k-image", UUID.randomUUID() + "." + format);
    addTmpFile(file, out);
    cmdContext.addArg(file.getAbsolutePath());

  }


  /**
   * @return io.github.kylinhunter.commons.cmd.ExecResult
   * @title run
   * @description run
   * @author BiJi'an
   * @date 2024-01-01 15:40
   */
  public ExecResult run() {
    ExecResult execResult = cmdExecutor.exec(this.cmdContext.getCmds());
    this.after(execResult);
    return execResult;
  }


  private void addTmpFile(File file, OutputStream out) {
    if (tmpFiles == null) {
      tmpFiles = ListUtils.newArrayList();
    }
    tmpFiles.add(new GmFile(file, out));
  }

  /**
   * @param execResult execResult
   * @title after
   * @description after
   * @author BiJi'an
   * @date 2024-01-01 00:13
   */
  private void after(ExecResult execResult) {
    if (tmpFiles == null) {
      return;
    }
    for (GmFile gmFile : tmpFiles) {
      log.info("delete tmp gmFile = {}", gmFile);

      OutputStream out = gmFile.getOut();
      if (execResult.isSuccess() && out != null) {
        try (FileInputStream in = gmFile.getFileInputStream()) {
          IOUtil.copy(in, out);
        } catch (Exception e) {
          throw new ImageException("after error", e);
        }
      }

      gmFile.delete();
    }

  }
}