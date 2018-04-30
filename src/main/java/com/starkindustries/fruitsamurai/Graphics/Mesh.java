package com.starkindustries.fruitsamurai.Graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {

	private final int vaoId;
	private final List<Integer> vboIdList;
	private final int vertexCount;
	private Texture texture;
	private Vector3f color;
	private Vector3f DEFAULT_COLOR = new Vector3f(1f, 1f, 1f);

	public Mesh(float[] positions, int[] indices, float[] textcoords, float[] normals) {

		FloatBuffer verticesBuffer = null;
		FloatBuffer textureBuffer = null;
		FloatBuffer normalsBuffer = null;
		IntBuffer indicesBuffer = null;
		try {
			if (color == null)
				color = DEFAULT_COLOR;

			vertexCount = indices.length;
			vboIdList = new ArrayList<>();

			vaoId = glGenVertexArrays();
			glBindVertexArray(vaoId);

			// Position VBO
			int vboId = glGenBuffers();
			vboIdList.add(vboId);
			verticesBuffer = MemoryUtil.memAllocFloat(positions.length);
			verticesBuffer.put(positions).flip();
			glBindBuffer(GL_ARRAY_BUFFER, vboId);
			glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
			glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

			// Texture coordinates VBO
			vboId = glGenBuffers();
			vboIdList.add(vboId);
			textureBuffer = MemoryUtil.memAllocFloat(textcoords.length);
			textureBuffer.put(textcoords).flip();
			glBindBuffer(GL_ARRAY_BUFFER, vboId);
			glBufferData(GL_ARRAY_BUFFER, textureBuffer, GL_STATIC_DRAW);
			glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
			
			// Normals VBO
			vboId = glGenBuffers();
			vboIdList.add(vboId);
			normalsBuffer = MemoryUtil.memAllocFloat(normals.length);
			normalsBuffer.put(normals).flip();
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, normalsBuffer, GL_STATIC_DRAW);
			glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);

			// Index VBO
			vboId = glGenBuffers();
			vboIdList.add(vboId);
			indicesBuffer = MemoryUtil.memAllocInt(indices.length);
			indicesBuffer.put(indices).flip();
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

			glBindBuffer(GL_ARRAY_BUFFER, 0);
			glBindVertexArray(0);
		} finally {
			if (verticesBuffer != null) {
				MemoryUtil.memFree(verticesBuffer);
			}
			if (indicesBuffer != null) {
				MemoryUtil.memFree(indicesBuffer);
			}
			if (textureBuffer != null) {
				MemoryUtil.memFree(textureBuffer);
			}
			if (normalsBuffer != null) {
				MemoryUtil.memFree(textureBuffer);
			}
		}
	}

	public boolean isTextured() {
		return this.texture != null;
	}

	public Texture getTexture() {
		return this.texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}

	public Vector3f getColor() {
		return this.color;
	}

	public int getVaoId() {
		return vaoId;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public void render() {
		if (texture != null) {
			// Activate firs texture bank
			glActiveTexture(GL_TEXTURE0);
			// Bind the texture
			glBindTexture(GL_TEXTURE_2D, texture.getID());
		}

		// Draw the mesh
		glBindVertexArray(getVaoId());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);

		glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);

		// Restore state
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public void cleanUp() {
		glDisableVertexAttribArray(0);
		// Delete the VBO
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		for (int vboId : vboIdList) {
			glDeleteBuffers(vboId);
		}
		if (texture != null)
			texture.cleanup();
		// Delete the VAO
		glBindVertexArray(0);
		glDeleteVertexArrays(vaoId);
	}

}
