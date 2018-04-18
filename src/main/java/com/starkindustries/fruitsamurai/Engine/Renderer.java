package com.starkindustries.fruitsamurai.Engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import com.starkindustries.fruitsamurai.Graphics.Mesh;
import com.starkindustries.fruitsamurai.Graphics.Shader;
import com.starkindustries.fruitsamurai.Utils.FileUtils;

public class Renderer {

	private Shader shaderProgram;
	
    public Renderer()
    {
    }

    public void init() throws Exception
    {
    	shaderProgram = new Shader();
    	shaderProgram.createVertexShader(FileUtils.loadAsString(FileUtils.getShadersFolder()+"\\bg_def.vs"));
    	shaderProgram.createFragmentShader(FileUtils.loadAsString(FileUtils.getShadersFolder()+"\\bg_def.fs"));
    	shaderProgram.link();
    }

    public void clear()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
    
    public void render(Window window)
    {
    	clear();

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }
    }
    
    public void render(Mesh mesh)
    {
    	shaderProgram.bind();
    	// Draw the mesh
    	glBindVertexArray(mesh.getVaoId());
    	glEnableVertexAttribArray(0);
    	glEnableVertexAttribArray(1);
    	glDrawElements(GL_TRIANGLES, mesh.getVertexCount(),GL_UNSIGNED_INT, 0);
    	// Restore state
    	glDisableVertexAttribArray(0);
    	glBindVertexArray(0);
    	shaderProgram.unbind();

    }
    
    public void cleanup() 
    {
    	if (shaderProgram != null) 
    	{
            shaderProgram.cleanup();
        }
    }   
}
