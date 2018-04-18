/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starkindustries.fruitsamurai.Graphics;


import java.util.HashMap;
import java.util.Map;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import static org.lwjgl.opengl.GL20.*;

/**
 *
 * @author Raóland
 */
public class Shader {
	private final int ID;
	private int vertexShaderId;
	private int fragmentShaderId;

	private Map<String, Integer> locationCache = new HashMap<String, Integer>();

	public Shader() throws Exception {
		ID = glCreateProgram();
		if (ID == 0)
			throw new Exception("Could not create Shader");
	}

	public void createVertexShader(String shaderCode) throws Exception {
		vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
	}

	public void createFragmentShader(String shaderCode) throws Exception {
		fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
	}

	protected int createShader(String shaderCode, int shaderType) throws Exception {
		int shaderId = glCreateShader(shaderType);

		if (shaderId == 0) {
			throw new Exception("Error creating shader. Type: " + shaderType);
		}

		glShaderSource(shaderId, shaderCode);
		glCompileShader(shaderId);

		if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
			throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
		}

		glAttachShader(ID, shaderId);
		return shaderId;
	}

	public void link() throws Exception {
		glLinkProgram(ID);
		if (glGetProgrami(ID, GL_LINK_STATUS) == 0) {
			throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(ID, 1024));
		}
		if (vertexShaderId != 0) {
			glDetachShader(ID, vertexShaderId);
		}
		if (fragmentShaderId != 0) {
			glDetachShader(ID, fragmentShaderId);
		}
		glValidateProgram(ID);
		if (glGetProgrami(ID, GL_VALIDATE_STATUS) == 0) {
			System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(ID, 1024));
		}
	}

	public void bind() {
		glUseProgram(ID);
	}

	public void unbind() {
		glUseProgram(0);
	}

	public int getUniform(String name) {
		if (locationCache.containsKey(name)) {
			return locationCache.get(name);
		}
		int result = glGetUniformLocation(ID, name);
		if (result == -1) {
			System.err.println("Could not find uniform variable '" + name + "' .");
		} else {
			locationCache.put(name, result);
		}
		return result;
	}

	public void setUniform1i(String name, int value) {
		glUniform1i(getUniform(name), value);
	}

	public void setUniform1f(String name, float value) {
		glUniform1f(getUniform(name), value);
	}

	public void setUniform2f(String name, float x, float y) {
		glUniform2f(getUniform(name), x, y);
	}

	public void setUniform3f(String name, Vector3f vec) {
		glUniform3f(getUniform(name), vec.x, vec.y, vec.z);
	}

	public void setUniformMat4f(String name, Matrix4f mat) {
		float[] buffer = new float[16];
		mat.get(buffer);
		glUniformMatrix4fv(getUniform(name), false, buffer);
	}

	public void cleanup() {
		unbind();
		if (ID != 0) {
			glDeleteProgram(ID);
		}
	}

}
