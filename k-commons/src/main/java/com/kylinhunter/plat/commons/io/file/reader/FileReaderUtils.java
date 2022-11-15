package com.kylinhunter.plat.commons.io.file.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.Charsets;

import com.kylinhunter.plat.commons.exception.inner.KIOException;

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
    public static <T> T process(File file, String encoding, DefaultLineProcessor<T> processor) {

        try (InputStream input = new FileInputStream(file)) {
            process(input, encoding, processor);
            return processor.getResult();
        } catch (Exception e) {
            throw new KIOException("process error", e);
        }

    }

}
