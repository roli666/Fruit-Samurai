/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starkindustries.fruitsamurai.Utils;

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
    private static Logger logger = LoggerFactory.getLogger("com.starkindustries.fruitsamurai.Utils.FileUtils");
    /**
     * Loads a file and creates a String from it
     * @param file
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @return string from file
     */
    public static String loadAsString(URL file)
    {
        logger.debug("URL:",file);
        StringBuilder result = new StringBuilder();
        try 
        {
            BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));
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
     * @param file
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @return string from file as {@link List}
     */
    public static List<String> loadAsStringList(URL file)
    {
        logger.debug("URL:",file);
        List<String> result = new ArrayList<>();
        try 
        {
            BufferedReader reader = new BufferedReader(new FileReader(file.getFile()));
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
    public static ByteBuffer ioResourceToByteBuffer(URL resource, int bufferSize) throws IOException {
        logger.debug("URL:",resource);
        ByteBuffer buffer;

        Path path = Paths.get(resource.getPath().substring(1));
        if (Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = BufferUtils.createByteBuffer((int) fc.size() + 1);
                while (fc.read(buffer) != -1) ;
            }
        } else {
            try (
                    InputStream source = resource.openStream();
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
        }

        buffer.flip();
        return buffer;
    }
}
