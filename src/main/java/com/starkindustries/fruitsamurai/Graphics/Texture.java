/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starkindustries.fruitsamurai.Graphics;

import com.starkindustries.fruitsamurai.Utils.BufferUtils;
import de.matthiasmann.twl.utils.PNGDecoder;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL12.*;

/**
 * This class represents a Texture.
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class Texture {
    private int width, height;
    private final int texture;
    private int size;

    /**
     * Constructor that loads a texture from an image.
     * @param path
     * @throws Exception
     */
    public Texture(URL path) throws Exception {
        texture = load(path);
    }

    /**
     * Creates an empty texture.
     * @param width       Width of the texture
     * @param height      Height of the texture
     * @param pixelFormat Specifies the format of the pixel data (GL_RGBA, etc.)
     * @throws Exception
     */
    public Texture(int width, int height, int pixelFormat) throws Exception {
        this.texture = glGenTextures();
        this.width = width;
        this.height = height;
        glBindTexture(GL_TEXTURE_2D, this.texture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, this.width, this.height, 0, pixelFormat, GL_FLOAT, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    }
    /**
     * Loads a texture from an InputStream
     * @param is
     * @throws Exception
     */
    public Texture(InputStream is) throws Exception {
        // Load Texture file
        PNGDecoder decoder = new PNGDecoder(is);

        this.width = decoder.getWidth();
        this.height = decoder.getHeight();

        // Load texture contents into a byte buffer
        ByteBuffer buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
        decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
        buf.flip();

        // Create a new OpenGL texture
        this.texture = glGenTextures();
        // Bind the texture
        glBindTexture(GL_TEXTURE_2D, this.texture);

        // Tell OpenGL how to unpack the RGBA bytes. Each component is 1 byte size
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        // Upload the texture data
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
        // Generate Mip Map
        glGenerateMipmap(GL_TEXTURE_2D);
    }
    /**
     * Loads a texture from an image.
     * @param path
     * @throws Exception
     */
    private int load(URL path) throws Exception {
        int[] pixels = null;
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(path.getFile()));
            width = image.getWidth();
            height = image.getHeight();
            size = width * height;
            pixels = new int[size];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            int a = (pixels[i] & 0xff000000) >> 24;
            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0xff00) >> 8;
            int b = (pixels[i] & 0xff);

            data[i] = a << 24 | b << 16 | g << 8 | r;
        }

        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
        glGenerateMipmap(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, 0);
        return textureID;
    }

    /**
     * Gets the id
     * @return the ID of the {@link Texture}
     */
    public int getID() {
        return texture;
    }

    /**
     * Gets the width
     * @return the width of the {@link Texture}
     */
    public int getWidth(){return width;}
    /**
     * Gets the height
     * @return the height of the {@link Texture}
     */
    public int getHeight(){return height;}
    /**
     * Cleans up the {@link Texture}.
     */
    public void cleanup() {
        glDeleteTextures(texture);
    }
}
