package com.starkindustries.fruitsamurai.Engine;

import static org.lwjgl.opengl.GL11.*;
//import static org.lwjgl.opengl.GL20.*;
//import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;

import com.starkindustries.fruitsamurai.Graphics.GameItem;
import com.starkindustries.fruitsamurai.Graphics.Mesh;
//import com.starkindustries.fruitsamurai.Graphics.Mesh;
import com.starkindustries.fruitsamurai.Graphics.Shader;
import com.starkindustries.fruitsamurai.Graphics.Transformations;
import com.starkindustries.fruitsamurai.Utils.FileUtils;
import org.joml.Vector4f;
/**
 * This class manages rendering to the given {@link Window}
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class Renderer {

    private Shader shaderProgram;
    private Matrix4f projection_matrix;
    private Transformations transformation;
    private Vector4f ambient_light;
    /**
     * Standard constructor initializes the transformations.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public Renderer() {
        transformation = new Transformations();
    }
    /**
     * Initializes the orthographic projection matrix and the shaders.
     * @param window
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void init(Window window) throws Exception {
        ambient_light = new Vector4f(1, 1, 1, 1);
        projection_matrix = transformation.getProjectionMatrixOrtho(-16, 16, 16, -16, 0, 16);

        setupWorldShader();
    }
    /**
     * Initializes the World Shader
     * @throws Exception
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    private void setupWorldShader() throws Exception {
        shaderProgram = new Shader();
        shaderProgram.createVertexShader(FileUtils.loadAsString(FileUtils.getShadersFolder() + "bg_def.vs"));
        shaderProgram.createFragmentShader(FileUtils.loadAsString(FileUtils.getShadersFolder() + "bg_def.fs"));
        shaderProgram.link();

        shaderProgram.createUniform("projection_matrix");
        shaderProgram.createUniform("world_matrix");
        shaderProgram.createUniform("texture_sampler");
        shaderProgram.createUniform("color");
        shaderProgram.createUniform("use_color");
        shaderProgram.createUniform("ambient_light");
    }
    /**
     * Clears the OpenGL buffers, the game uses.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    }
    /**
     * Renders the World from the given list of {@link GameItem} objects.
     * Also changes the viewport when the window gets resized.
     * Calls the {@link #renderWorld(Window, List)} method.
     * @param window
     * @param items
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void render(Window window, List<GameItem> items) {
        clear();

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        renderWorld(window, items);
    }
    /**
     * Renders the World from the given list of {@link GameItem} objects, and binds the shaders.
     * Also sets the uniforms that the shader needs.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    private void renderWorld(Window window, List<GameItem> items) {
        shaderProgram.bind();
        shaderProgram.setUniform1i("texture_sampler", 0);
        shaderProgram.setUniformMat4f("projection_matrix", projection_matrix);
        for (GameItem gameItem : items) {
            if (gameItem.visible) {
                List <Mesh> meshes = new ArrayList<>();
                if(gameItem.getMeshes() != null)
                    for(Mesh messy : gameItem.getMeshes())
                        meshes.add(messy);
                if(gameItem.getMesh() !=  null)
                    meshes.add(gameItem.getMesh());
                for(Mesh moremessy : meshes)
                {
                // Set world matrix for this item
                Matrix4f worldMatrix = transformation.getWorldMatrix(
                        gameItem.getPosition(),
                        gameItem.getRotation(),
                        gameItem.getScale()
                );
                shaderProgram.setUniformMat4f("world_matrix", worldMatrix);
                shaderProgram.setUniform3f("color", moremessy.getColor());
                shaderProgram.setUniform1i("use_color", moremessy.hasMaterial() ? 0 : 1);
                shaderProgram.setUniform4f("ambient_light", ambient_light);
                // Render the mesh for this game item
                    moremessy.render();
                }
            }
        }
        shaderProgram.unbind();
    }
    /**
     * Cleans up the shader.
     * @see Shader#cleanup() for more information.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }
    /**
     *@param ambient_light Sets light from a {@link org.joml.Vector3f} object
     */
    public void setAmbient_light(Vector4f ambient_light) {
        this.ambient_light = ambient_light;
    }

    /**
     * @return current ambient_light
     */
    public Vector4f getAmbient_light() {
        return ambient_light;
    }

    ;
}
