/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starkindustries.fruitsamurai.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Raóland
 */
public class FileUtils {
    private FileUtils(){}

    private static final ClassLoader classLoader = FileUtils.class.getClassLoader();
    private static final File textures = new File(classLoader.getResource("textures").getPath());
    private static final File shaders = new File(classLoader.getResource("shaders").getPath());

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

    public static String getTexturesFolder(){return textures.toString();}
    public static String getShadersFolder(){return shaders.toString();}
}
