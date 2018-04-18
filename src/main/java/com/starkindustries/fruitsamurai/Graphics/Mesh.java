package com.starkindustries.fruitsamurai.Graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {
	private final int vaoId;
	private final int positionVBOId;
	private final int indexVBOId;
	private final int vertexCount;
	private final int colorVBOId;

	public Mesh(float[] positions, float[] colors, int[] indices) {
		FloatBuffer verticesBuffer = null;
		IntBuffer indicesBuffer = null;
		FloatBuffer colorBuffer = null;
		try {
			verticesBuffer = MemoryUtil.memAllocFloat(positions.length);
			vertexCount = indices.length;
			verticesBuffer.put(positions).flip();
			vaoId = glGenVertexArrays();
			glBindVertexArray(vaoId);
			positionVBOId = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, positionVBOId);
			glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
			glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			
			indexVBOId = glGenBuffers();
			indicesBuffer = MemoryUtil.memAllocInt(indices.length);
			indicesBuffer.put(indices).flip();
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVBOId);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
			
			colorVBOId = glGenBuffers();
			colorBuffer = MemoryUtil.memAllocFloat(colors.length);
			colorBuffer.put(colors).flip();
			glBindBuffer(GL_ARRAY_BUFFER, colorVBOId);
			glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
			glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
			
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			glBindVertexArray(0);
		} finally {
			if (verticesBuffer != null) {
				MemoryUtil.memFree(verticesBuffer);
			}
			if (indicesBuffer != null) {
				MemoryUtil.memFree(indicesBuffer);
			}
			if (colorBuffer != null) {
				MemoryUtil.memFree(colorBuffer);
			}
		}
	}

	public int getVaoId() {
		return vaoId;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public void cleanUp() {
		glDisableVertexAttribArray(0);
		// Delete the VBO
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteBuffers(positionVBOId);
		glDeleteBuffers(indexVBOId);
		// Delete the VAO
		glBindVertexArray(0);
		glDeleteVertexArrays(vaoId);
	}

}
