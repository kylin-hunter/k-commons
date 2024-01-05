package io.github.kylinhunter.commons.images;

import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.io.file.TmpDirUtils;
import java.io.File;
import java.io.IOException;
import org.im4java.core.ConvertCmd;
import org.im4java.core.GMOperation;
import org.im4java.core.IM4JavaException;

class TestTest {

  public static void main(String[] args)
      throws IOException, InterruptedException, IM4JavaException {

    ConvertCmd convert = new ConvertCmd(true);

    GMOperation op = new GMOperation();

    File file = ResourceHelper.getFileInClassPath("jackie-chan.jpg");
    System.out.println(file.getAbsolutePath());

    op.addImage(file.getAbsolutePath());
    op.resize(100, 100);
    File tmpFile = TmpDirUtils.getUserFile("1.jpg");
    System.out.println(tmpFile.getAbsolutePath());
    op.addImage(tmpFile.getAbsolutePath());
    convert.run(op);
  }
}