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
 *
 * @author Raóland
 */
public class BufferUtils {
    private BufferUtils(){}
    
    public static ByteBuffer createByteBuffer(byte[] array)
    {
        ByteBuffer result = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
        result.put(array).flip();
        return result;
    }

    public static ByteBuffer createByteBuffer(int size)
    {
        ByteBuffer result = ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
        return result;
    }

    public static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

    public static IntBuffer createIntBuffer(int[] array)
    {
        IntBuffer result = ByteBuffer.allocateDirect(array.length*4).order(ByteOrder.nativeOrder()).asIntBuffer();
        result.put(array).flip();
        return result;
    }
    public static FloatBuffer createFloatBuffer(float[] array)
    {
        FloatBuffer result = ByteBuffer.allocateDirect(array.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        result.put(array).flip();
        return result;
    }
}
