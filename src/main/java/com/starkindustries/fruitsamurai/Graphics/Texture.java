/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starkindustries.fruitsamurai.Graphics;

import com.starkindustries.fruitsamurai.Utils.BufferUtils;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

/**
 *
 * @author Raóland
 */
public class Texture {
    private int width,height;
    private final int texture;
    private int size;

    public Texture(String path) throws Exception {
        texture = load(path);
    }

    private int load(String path) throws Exception
    {
        int[] pixels = null;
        try
        {
            BufferedImage image = ImageIO.read(new FileInputStream(path));
            width = image.getWidth();
            height = image.getHeight();
            size = width*height;
            pixels = new int[size];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
        int[] data = new int[size];
        for(int i=0;i<size;i++)
        {
            int a = (pixels[i] & 0xff000000) >> 24;
            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0xff00) >> 8;
            int b = (pixels[i] & 0xff);
            
            data[i] = a << 24 | b << 16 | g << 8 | r;
        }
        
        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D,textureID);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
        glGenerateMipmap(GL_TEXTURE_2D);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glBindTexture(GL_TEXTURE_2D,0);
        return textureID;
    }
    
    public int getID() {
    	return texture;
    }
    
    public void bind()
    {
        glBindTexture(GL_TEXTURE_2D,texture);
    }
    public void cleanup() {
        glDeleteTextures(texture);
    }
}
