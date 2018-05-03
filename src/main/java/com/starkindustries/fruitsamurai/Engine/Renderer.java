package com.starkindustries.fruitsamurai.Engine;

import static org.lwjgl.opengl.GL11.*;
//import static org.lwjgl.opengl.GL20.*;
//import static org.lwjgl.opengl.GL30.*;

import java.util.List;

import org.joml.Matrix4f;

import com.starkindustries.fruitsamurai.Graphics.GameItem;
import com.starkindustries.fruitsamurai.Graphics.Mesh;
//import com.starkindustries.fruitsamurai.Graphics.Mesh;
import com.starkindustries.fruitsamurai.Graphics.Shader;
import com.starkindustries.fruitsamurai.Graphics.Transformations;
import com.starkindustries.fruitsamurai.Utils.FileUtils;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Renderer {

	private Shader shaderProgram;
	private Matrix4f projection_matrix;
	private Transformations transformation;
	private Vector4f ambient_light;

	public Renderer() {
		transformation = new Transformations();
	}

	public void init(Window window) throws Exception {
		shaderProgram = new Shader();
		ambient_light = new Vector4f(1,1,1,1);
		shaderProgram.createVertexShader(FileUtils.loadAsString(FileUtils.getShadersFolder() + "\\bg_def.vs"));
		shaderProgram.createFragmentShader(FileUtils.loadAsString(FileUtils.getShadersFolder() + "\\bg_def.fs"));
		shaderProgram.link();
		projection_matrix = transformation.getProjectionMatrixOrtho(-16, 16, 16, -16, 0, 16);
		shaderProgram.createUniform("projection_matrix");
		shaderProgram.createUniform("world_matrix");
		shaderProgram.createUniform("texture_sampler");
		shaderProgram.createUniform("color");
		shaderProgram.createUniform("use_color");
		shaderProgram.createUniform("ambient_light");
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void render(Window window, List<GameItem> items) {
		clear();

		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}
		shaderProgram.bind();
		shaderProgram.setUniform1i("texture_sampler", 0);
		shaderProgram.setUniformMat4f("projection_matrix", projection_matrix);
		for (GameItem gameItem : items)
		{
			if(gameItem.visible)
			{
			Mesh mesh = gameItem.getMesh();
			// Set world matrix for this item
			Matrix4f worldMatrix = transformation.getWorldMatrix(
					gameItem.getPosition(),
					gameItem.getRotation(),
					gameItem.getScale()
					);
			shaderProgram.setUniformMat4f("world_matrix", worldMatrix);
			shaderProgram.setUniform3f("color", mesh.getColor());
			shaderProgram.setUniform1i("use_color", mesh.hasMaterial() ? 0 : 1);
			shaderProgram.setUniform4f("ambient_light", ambient_light);
			// Render the mesh for this game item
			mesh.render();
			}
		}
		shaderProgram.unbind();
	}

	public void cleanup() {
		if (shaderProgram != null) {
			shaderProgram.cleanup();
		}
	}

	public void setAmbient_light(Vector4f ambient_light) {
		this.ambient_light = ambient_light;
	}
	public Vector4f getAmbient_light(){return ambient_light;};
}
