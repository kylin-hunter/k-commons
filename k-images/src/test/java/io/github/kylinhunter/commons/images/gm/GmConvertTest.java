package io.github.kylinhunter.commons.images.gm;

import io.github.kylinhunter.commons.cmd.ExecResult;
import io.github.kylinhunter.commons.images.constant.Format;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.io.file.TmpDirUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GmConvertTest {

  @Test
  void test1() {

    File source = ResourceHelper.getFileInClassPath("/mac-settings.jpg");
    File dist = TmpDirUtils.getUserFile("k-images", "convert-1.png");

    GmConvert convert = GmFactory.convert();
    convert.size(100, 100);
    convert.addImageInput(source);

    convert.resize(100, 100);
    convert.depth(16);
    convert.removeProfile("*");

    convert.addImageOut(Path.of(dist.getAbsolutePath()));

    System.out.println(convert.cmdContext.toString());

    ExecResult execResult = convert.run();
    System.out.println(execResult);
    Assertions.assertTrue(execResult.isSuccess());
  }

  @Test
  void test2() throws FileNotFoundException {

    File source = ResourceHelper.getFileInClassPath("/mac-settings.jpg");
    FileInputStream input = new FileInputStream(source);

    File dist = TmpDirUtils.getUserFile("k-images", "convert-2.jpg");
    FileOutputStream output = new FileOutputStream(dist);

    GmConvert convert = GmFactory.convert();
    convert.size(100, 100);
    convert.addImageInput(input, Format.JPEG);

    convert.resize(100, 100);

    convert.addArg("-depth", "16");
    convert.addImageOut(output, Format.JPEG);

    System.out.println(convert.cmdContext.toString());

    ExecResult execResult = convert.run();
    System.out.println(execResult);
    Assertions.assertTrue(execResult.isSuccess());
  }

}