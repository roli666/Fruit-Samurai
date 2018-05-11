/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starkindustries.fruitsamurai.Utils;

import java.io.*;
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
    private static final ClassLoader classLoader = FileUtils.class.getClassLoader();
    private static final File textures = new File(classLoader.getResource("textures").getPath());
    private static final File shaders = new File(classLoader.getResource("shaders").getPath());
    private static final File meshes = new File(classLoader.getResource("meshes").getPath());
    private static final File fonts = new File(classLoader.getResource("fonts").getPath());
    /**
     * Loads a file and creates a String from it
     * @param file
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @return string from file
     */
    public static String loadAsString(String file)
    {
        StringBuilder result = new StringBuilder();
        try 
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
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
    public static List<String> loadAsStringList(String file)
    {
        List<String> result = new ArrayList<>();
        try 
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
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
        ByteBuffer buffer;

        Path path = Paths.get(resource);
        if (Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = BufferUtils.createByteBuffer((int) fc.size() + 1);
                while (fc.read(buffer) != -1) ;
            }
        } else {
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
        }

        buffer.flip();
        return buffer;
    }

    /**
     * @return The path to the textures folder.
     */
    public static String getTexturesFolder(){return textures.toString()+"\\";}
    /**
     * @return The path to the shaders folder.
     */
    public static String getShadersFolder(){return shaders.toString()+"\\";}
    /**
     * @return The path to the meshes folder.
     */
    public static String getMeshesFolder(){return meshes.toString()+"\\";}
    /**
     * @return The path to the fonts folder.
     */
    public static String getFontsFolder(){return fonts.toString()+"\\";}
    /**
     * @return The path to the resources folder.
     */
    public static String getResourcesFolder(){return classLoader.getResource("").getPath();}
}
