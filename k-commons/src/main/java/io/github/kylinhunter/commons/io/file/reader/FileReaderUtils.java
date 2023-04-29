package io.github.kylinhunter.commons.io.file.reader;

import io.github.kylinhunter.commons.exception.embed.KIOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class FileReaderUtils {

    /**
     * @param file      a file
     * @param encoding  the encoding of the file
     * @param processor a processor to process the file
     * @title process
     * @description
     * @author BiJi'an
     * @date 2022-01-01 01:48
     */
    public static void process(File file, String encoding, LineProcessor processor) {

        try (InputStream input = new FileInputStream(file)) {
            process(input, encoding, processor);
        } catch (Exception e) {
            throw new KIOException("process error", e);
        }

    }

    /**
     * @param input     the input stream
     * @param encoding  the encoding
     * @param processor the processor
     * @title process
     * @description
     * @author BiJi'an
     * @date 2022-01-01 02:00
     */
    public static void process(InputStream input, String encoding, LineProcessor processor) {

        try (InputStreamReader streamReader = new InputStreamReader(input, Charsets.toCharset(encoding));
             BufferedReader bufferReader = new BufferedReader(streamReader)) {
            String line = bufferReader.readLine();
            while (line != null) {
                processor.process(line);
                line = bufferReader.readLine();

            }
        } catch (Exception e) {
            throw new KIOException("process error", e);
        }
    }

    /**
     * @param file      a file
     * @param encoding  the encoding of the file
     * @param processor a processor to process the file
     * @title process
     * @description
     * @author BiJi'an
     * @date 2022-01-01 01:48
     */
    @SuppressWarnings("UnusedReturnValue")
    public static <T> T process(File file, String encoding, DefaultLineProcessor<T> processor) {

        try (InputStream input = new FileInputStream(file)) {
            process(input, encoding, processor);
            return processor.getResult();
        } catch (Exception e) {
            throw new KIOException("process error", e);
        }

    }

    /**
     * @param file file
     * @return byte[]
     * @title readFileToByteArray
     * @description
     * @author BiJi'an
     * @date 2023-04-22 00:32
     */
    public static byte[] readFileToByteArray(final File file) throws IOException {
        try (InputStream inputStream = openInputStream(file)) {
            final long fileLength = file.length();
            // file.length() may return 0 for system-dependent entities, treat 0 as unknown length - see IO-453
            return fileLength > 0 ? IOUtils.toByteArray(inputStream, fileLength) : IOUtils.toByteArray(inputStream);
        }
    }

    /**
     * @param file file
     * @return java.io.FileInputStream
     * @title openInputStream
     * @description
     * @author BiJi'an
     * @date 2023-04-22 00:32
     */
    public static FileInputStream openInputStream(final File file) throws IOException {
        Objects.requireNonNull(file, "file");
        return new FileInputStream(file);
    }

}
