package com.starkindustries.fruitsamurai.Engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.joml.Matrix4f;

import com.starkindustries.fruitsamurai.Graphics.GameItem;
import com.starkindustries.fruitsamurai.Graphics.Mesh;
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
		shaderProgram.createUniform("projection_matrix");
		shaderProgram.createUniform("world_matrix");
		// projection_matrix = new
		// Matrix4f().perspective((float)Math.toRadians(60),(float)window.getWidth()/window.getHeight(),
		// 0.01f, 1000.0f);

	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void render(Window window, GameItem[] items) {
		clear();

		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}
		shaderProgram.bind();
		shaderProgram.setUniformMat4f("projection_matrix", projection_matrix);
		for (GameItem gameItem : items) {
			// Set world matrix for this item
			Matrix4f worldMatrix = transformation.getWorldMatrix(
					gameItem.getPosition(),
					gameItem.getRotation(),
					gameItem.getScale()
					);
			shaderProgram.setUniformMat4f("worldMatrix", worldMatrix);
			// Render the mesh for this game item
			gameItem.getMesh().render();
		}
		shaderProgram.unbind();
	}

	public void cleanup() {
		if (shaderProgram != null) {
			shaderProgram.cleanup();
		}
	}
}
