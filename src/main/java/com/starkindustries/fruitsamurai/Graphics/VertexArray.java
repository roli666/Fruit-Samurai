/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starkindustries.fruitsamurai.Graphics;

import com.starkindustries.fruitsamurai.Utils.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 *
 * @author Raóland
 */
public class VertexArray {
    
    private int count;
    private int vao;
    private int vbo;
    private int ibo;
    private int tcs;
    
    public VertexArray(float[] vertices,byte[] indices,float[] textureCoordinates)
    {
        count = indices.length;
        vao=glGenVertexArrays();
        glBindVertexArray(vao);
        
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,vbo);
        glBufferData(GL_ARRAY_BUFFER,BufferUtils.createFloatBuffer(vertices),GL_STATIC_DRAW);
        //glVertexAttribPointer(Shader.VERTEXLOCATION, 3, GL_FLOAT, false, 0, 0);
        //glEnableVertexAttribArray(Shader.VERTEXLOCATION);
        
        tcs = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,tcs);
        glBufferData(GL_ARRAY_BUFFER,BufferUtils.createFloatBuffer(textureCoordinates),GL_STATIC_DRAW);
        //glVertexAttribPointer(Shader.TEXTURELOCATION, 3, GL_FLOAT, false, 0, 0);
        //glEnableVertexAttribArray(Shader.TEXTURELOCATION);
        
        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,BufferUtils.createByteBuffer(indices),GL_STATIC_DRAW);
        
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
        glBindBuffer(GL_ARRAY_BUFFER,0);
        glBindVertexArray(0);
    }
    public void bind()
    {
        glBindVertexArray(vao);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
    }
    public void unbind()
    {
        glBindVertexArray(0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }
    public void draw()
    {
        glDrawElements(GL_TRIANGLES,count,GL_UNSIGNED_BYTE,0);
    }
    public void render()
    {
        bind();
        draw();
    }
    
}
