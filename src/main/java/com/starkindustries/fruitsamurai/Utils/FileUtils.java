/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starkindustries.fruitsamurai.Utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a utility class to make my life easier and to keep the code more clean.
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class.getName());
    /**
     * Loads a file and creates a String from it
     * @param filepath
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @return string from file
     */
    public static String loadAsString(String filepath)
    {
        logger.debug("Path to file: {}",filepath);
        StringBuilder result = new StringBuilder();
        try 
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtils.class.getResourceAsStream(filepath)));
            String buffer = "";
            while((buffer = reader.readLine())!=null)
            {
                result.append(buffer + '\n');
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return result.toString();
    }
    /**
     * Loads a file and creates a List from it
     * @param filepath
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @return string from file as {@link List}
     */
    public static List<String> loadAsStringList(String filepath)
    {
        logger.debug("Path to file: {}",filepath);
        List<String> result = new ArrayList<>();
        try 
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtils.class.getResourceAsStream(filepath)));
            String buffer = "";
            while((buffer = reader.readLine())!=null)
            {
                result.add(buffer);
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * Loads a file and creates a ByteBuffer from it
     * @param resource
     * @param bufferSize
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @return string from file as a {@link ByteBuffer}
     * @throws IOException
     */
    public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        logger.debug("Path to file: {}",resource);
        ByteBuffer buffer;
            try (
                    InputStream source = FileUtils.class.getResourceAsStream(resource);
                    ReadableByteChannel rbc = Channels.newChannel(source)) {
                buffer = BufferUtils.createByteBuffer(bufferSize);

                while (true) {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    if (buffer.remaining() == 0) {
                        buffer = BufferUtils.resizeBuffer(buffer, buffer.capacity() * 2);
                    }
                }
            }
        buffer.flip();
        return buffer;
    }
    public static File stream2file (InputStream in) throws IOException {
        final String PREFIX = "stream2file";
        final String SUFFIX = ".obj";
        final File tempFile = File.createTempFile(PREFIX, SUFFIX);
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return tempFile;
    }
}
