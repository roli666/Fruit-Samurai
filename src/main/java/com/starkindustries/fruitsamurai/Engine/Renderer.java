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

public class Renderer {

	private Shader shaderProgram;
	private Matrix4f projection_matrix;
	private Transformations transformation;

	public Renderer() {
		transformation = new Transformations();
	}

	public void init(Window window) throws Exception {
		shaderProgram = new Shader();
		shaderProgram.createVertexShader(FileUtils.loadAsString(FileUtils.getShadersFolder() + "\\bg_def.vs"));
		shaderProgram.createFragmentShader(FileUtils.loadAsString(FileUtils.getShadersFolder() + "\\bg_def.fs"));
		shaderProgram.link();
		projection_matrix = new Matrix4f().ortho(-10, 10, 10, -10, 0, 10);
		//projection_matrix = new
		//Matrix4f().perspective((float)Math.toRadians(60),(float)window.getWidth()/window.getHeight(),
		// 0.01f, 1000.0f);
		shaderProgram.createUniform("projection_matrix");
		shaderProgram.createUniform("world_matrix");
		shaderProgram.createUniform("texture_sampler");
		shaderProgram.createUniform("color");
		shaderProgram.createUniform("use_color");
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
		for (GameItem gameItem : items) {
			Mesh mesh = gameItem.getMesh();
			// Set world matrix for this item
			Matrix4f worldMatrix = transformation.getWorldMatrix(
					gameItem.getPosition(),
					gameItem.getRotation(),
					gameItem.getScale()
					);
			shaderProgram.setUniformMat4f("world_matrix", worldMatrix);
			shaderProgram.setUniform3f("color", mesh.getColor());
			shaderProgram.setUniform1i("use_color", mesh.isTextured() ? 0 : 1);
			// Render the mesh for this game item
			mesh.render();
		}
		shaderProgram.unbind();
	}

	public void cleanup() {
		if (shaderProgram != null) {
			shaderProgram.cleanup();
		}
	}
}
