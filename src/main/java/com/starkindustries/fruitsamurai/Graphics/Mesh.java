package com.starkindustries.fruitsamurai.Graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import com.starkindustries.fruitsamurai.Utils.ListUtils;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
/**
 * This class represents a Mesh.
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class Mesh {

    private final int vaoId;
    private final List<Integer> vboIdList;
    private final int vertexCount;
    private Vector3f color;
    private Vector3f DEFAULT_COLOR = new Vector3f(1f, 1f, 1f);
    private Material mat;

    /**
     * Standard constructor of the {@link Mesh} class.
     * @param positions
     * @param indices
     * @param textcoords
     * @param normals
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
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
                MemoryUtil.memFree(normalsBuffer);
            }
        }
    }
    /**
     * Standard constructor of the {@link Mesh} class.
     * @param positions
     * @param indices
     * @param textcoords
     * @param normals
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public Mesh(List<Float> positions, List<Integer> indices, List<Float> textcoords, List<Float> normals) {

        FloatBuffer verticesBuffer = null;
        FloatBuffer textureBuffer = null;
        FloatBuffer normalsBuffer = null;
        IntBuffer indicesBuffer = null;
        try {
            if (color == null)
                color = DEFAULT_COLOR;

            vertexCount = indices.size();
            vboIdList = new ArrayList<>();

            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            // Position VBO
            int vboId = glGenBuffers();
            vboIdList.add(vboId);
            verticesBuffer = MemoryUtil.memAllocFloat(positions.size());
            verticesBuffer.put(ListUtils.listToFloatArray(positions)).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            // Texture coordinates VBO
            vboId = glGenBuffers();
            vboIdList.add(vboId);
            textureBuffer = MemoryUtil.memAllocFloat(textcoords.size());
            textureBuffer.put(ListUtils.listToFloatArray(textcoords)).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, textureBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

            // Normals VBO
            vboId = glGenBuffers();
            vboIdList.add(vboId);
            normalsBuffer = MemoryUtil.memAllocFloat(normals.size());
            normalsBuffer.put(ListUtils.listToFloatArray(normals)).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, normalsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);

            // Index VBO
            vboId = glGenBuffers();
            vboIdList.add(vboId);
            indicesBuffer = MemoryUtil.memAllocInt(indices.size());
            indicesBuffer.put(ListUtils.listIntToArray(indices)).flip();
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
                MemoryUtil.memFree(normalsBuffer);
            }
        }
    }
    /**
     * @param color sets the color of the{@link Mesh}
     */
    public void setColor(Vector3f color) {
        this.color = color;
    }
    /**
     * @param mat sets the {@link Material} of the{@link Mesh}
     */
    public void setMaterial(Material mat) {
        this.mat = mat;
    }
    /**
     * @return the {@link Material} from the {@link Mesh}
     */
    public Material getMaterial() {
        return mat;
    }
    /**
     * @return whether the {@link Mesh} has a {@link Material} or not.
     */
    public boolean hasMaterial() {
        return mat != null;
    }
    /**
     * @return the color of the {@link Mesh}
     */
    public Vector3f getColor() {
        return this.color;
    }
    /**
     * @return the vertex array object id of the {@link Mesh}
     */
    public int getVaoId() {
        return vaoId;
    }
    /**
     * @return vertex count from the {@link Mesh}
     */
    public int getVertexCount() {
        return vertexCount;
    }

    /**
     * This method initializes the {@link Mesh} to render.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    protected void initRender() {
        Texture texture = mat != null ? mat.getTexture() : null;
        if (texture != null) {
            // Activate first texture bank
            glActiveTexture(GL_TEXTURE0);
            // Bind the texture
            glBindTexture(GL_TEXTURE_2D, texture.getID());
        }
        Texture normalMap = mat != null ? mat.getNormalMap() : null;
        if (normalMap != null) {
            // Activate second texture bank
            glActiveTexture(GL_TEXTURE1);
            // Bind the texture
            glBindTexture(GL_TEXTURE_2D, normalMap.getID());
        }

        // Draw the mesh
        glBindVertexArray(getVaoId());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
    }
    /**
     * This method gets called when the rendering has ended.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    protected void endRender() {
        // Restore state
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);

        glBindTexture(GL_TEXTURE_2D, 0);
    }
    /**
     * This method renders the mesh.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void render() {
        initRender();

        glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);

        endRender();
    }
    /**
     * This method cleans up the mesh.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void cleanUp() {
        glDisableVertexAttribArray(0);

        // Delete the VBOs
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        for (int vboId : vboIdList) {
            glDeleteBuffers(vboId);
        }

        // Delete the texture
        Texture texture = mat.getTexture();
        if (texture != null) {
            texture.cleanup();
        }

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }
    /**
     * This method deletes the buffers.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void deleteBuffers() {
        glDisableVertexAttribArray(0);

        // Delete the VBOs
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        for (int vboId : vboIdList) {
            glDeleteBuffers(vboId);
        }

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

}
