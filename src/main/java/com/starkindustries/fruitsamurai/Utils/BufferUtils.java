/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starkindustries.fruitsamurai.Utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * This class is a utility class to make my life easier and to keep the code more clean.
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class BufferUtils {
    private BufferUtils(){}

    /**
     * Creates a ByteBuffer from the supplied byte array.
     * @param array
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @return ByteBuffer
     */
    public static ByteBuffer createByteBuffer(byte[] array)
    {
        ByteBuffer result = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
        result.put(array).flip();
        return result;
    }
    /**
     * Creates a ByteBuffer that has a specific size.
     * @param size
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @return ByteBuffer
     */
    public static ByteBuffer createByteBuffer(int size)
    {
        ByteBuffer result = ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
        return result;
    }
    /**
     * Resizes a ByteBuffer to a specific size.
     * @param newCapacity
     * @param buffer
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @return ByteBuffer
     */
    public static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }
    /**
     * Creates a IntBuffer from the supplied int array.
     * @param array
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @return IntBuffer
     */
    public static IntBuffer createIntBuffer(int[] array)
    {
        IntBuffer result = ByteBuffer.allocateDirect(array.length*4).order(ByteOrder.nativeOrder()).asIntBuffer();
        result.put(array).flip();
        return result;
    }
    /**
     * Creates a FloatBuffer from the supplied float array.
     * @param array
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @return FloatBuffer
     */
    public static FloatBuffer createFloatBuffer(float[] array)
    {
        FloatBuffer result = ByteBuffer.allocateDirect(array.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        result.put(array).flip();
        return result;
    }
}
