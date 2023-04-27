package io.github.kylinhunter.commons.compress;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.IOUtils;

import io.github.kylinhunter.commons.io.file.FileUtil;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-06 22:31
 **/
public class ZipUtils {
    /**
     * @param files   files
     * @param zipFile zipFile
     * @return java.io.File
     * @title zip
     * @description
     * @author BiJi'an
     * @date 2023-04-22 23:10
     */
    public static File zip(List<File> files, File zipFile) throws IOException {
        return zip(files, null, zipFile);
    }

    /**
     * @param files   files
     * @param zipFile zipFile
     * @return java.io.File
     * @title doZip
     * @description
     * @author BiJi'an
     * @date 2022-11-06 23:37
     */
    public static File zip(List<File> files, File rootDir, File zipFile) throws IOException {
        if (!zipFile.getParentFile().exists()) {
            FileUtil.forceMkdirParent(zipFile);
        }
        Objects.requireNonNull(files, "files");
        Path rootPath = rootDir != null ? rootDir.toPath() : null;

        try (ZipArchiveOutputStream zos = new ZipArchiveOutputStream(zipFile)) {
            zos.setUseZip64(Zip64Mode.AsNeeded);
            for (File file : files) {
                String entryName = getRelativePath(rootPath, file);
                ZipArchiveEntry entry = new ZipArchiveEntry(file, entryName);
                zos.putArchiveEntry(entry);
                try (InputStream inputStream = new FileInputStream(file)) {
                    IOUtils.copy(inputStream, zos);
                    zos.closeArchiveEntry();
                }
            }
            zos.finish();
        } catch (Exception e) {
            throw new IOException("zip error", e);
        }
        return zipFile;
    }

    /**
     * @param rootPath rootPath
     * @param file     file
     * @return java.lang.String
     * @title getRelativePath
     * @description
     * @author BiJi'an
     * @date 2023-04-22 23:45
     */
    private static String getRelativePath(Path rootPath, File file) {
        if (rootPath != null) {
            Path filePath = file.toPath();
            int index = filePath.toString().indexOf(rootPath.toString());
            if (index >= 0) {
                return rootPath.relativize(filePath).toString();
            }
        }
        return file.getName();

    }

    /**
     * @param file      file
     * @param unzipPath unzipPath
     * @return void
     * @title unzip
     * @description
     * @author BiJi'an
     * @date 2022-11-07 00:00
     */
    public static void unzip(File file, File unzipPath) throws IOException {
        ZipFile zipFile = new ZipFile(file);
        try {
            byte[] buffer = new byte[IOUtils.DEFAULT_BUFFER_SIZE];
            Enumeration<ZipArchiveEntry> zipFileEntries = zipFile.getEntries();
            while (zipFileEntries.hasMoreElements()) {
                ZipArchiveEntry entry = zipFileEntries.nextElement();
                if (!entry.isDirectory()) {
                    File outputFile = FileUtil.getFile(unzipPath, true, entry.getName());
                    if (!outputFile.getParentFile().exists()) {
                        FileUtil.forceMkdirParent(outputFile);
                    }
                    try (InputStream input = zipFile.getInputStream(entry);
                         FileOutputStream fos = new FileOutputStream(outputFile)) {
                        IOUtils.copyLarge(input, fos, buffer);
                    }
                }
            }
        } catch (Exception e) {
            throw new IOException("unzip error", e);
        } finally {
            IOUtils.closeQuietly(zipFile);
        }
    }

    /**
     * @param content   content
     * @param unzipPath unzipPath
     * @return void
     * @title unzip
     * @description
     * @author BiJi'an
     * @date 2023-04-22 23:58
     */
    public static void unzip(byte[] content, File unzipPath) throws IOException {

        try (ZipArchiveInputStream zipArchiveInputStream = new ZipArchiveInputStream(
                new ByteArrayInputStream(content))) {
            byte[] buffer = new byte[IOUtils.DEFAULT_BUFFER_SIZE];

            ZipArchiveEntry entry;

            while ((entry = zipArchiveInputStream.getNextZipEntry()) != null) {
                if (!entry.isDirectory()) {
                    File outputFile = FileUtil.getFile(unzipPath, true, entry.getName());
                    if (!outputFile.getParentFile().exists()) {
                        FileUtil.forceMkdirParent(outputFile);
                    }
                    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                        IOUtils.copyLarge(zipArchiveInputStream, fos, buffer);
                    }
                }
            }
        } catch (Exception e) {
            throw new IOException("unzip error", e);
        }
    }
}