package com.starkindustries.fruitsamurai.Engine;

import static org.lwjgl.opengl.GL11.*;
//import static org.lwjgl.opengl.GL20.*;
//import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.List;

import com.starkindustries.fruitsamurai.Interfaces.IHud;
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
    private Shader hudShaderProgram;
    private Matrix4f projection_matrix;
    private Transformations transformation;
    private Vector4f ambient_light;

    public Renderer() {
        transformation = new Transformations();
    }

    public void init(Window window) throws Exception {
        ambient_light = new Vector4f(1, 1, 1, 1);
        projection_matrix = transformation.getProjectionMatrixOrtho(-16, 16, 16, -16, 0, 16);

        setupHudShader();
        setupWorldShader();
    }

    private void setupHudShader() throws Exception {
        hudShaderProgram = new Shader();
        hudShaderProgram.createVertexShader(FileUtils.loadAsString(FileUtils.getShadersFolder()+"hud.vs"));
        hudShaderProgram.createFragmentShader(FileUtils.loadAsString(FileUtils.getShadersFolder()+"hud.fs"));
        hudShaderProgram.link();

        hudShaderProgram.createUniform("projection_matrix");
        hudShaderProgram.createUniform("color");
        hudShaderProgram.createUniform("hasTexture");
    }
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

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, List<GameItem> items, IHud hud) {
        clear();

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        renderHud(window, hud);
        renderWorld(window, items);
    }

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

    private void renderHud(Window window, IHud hud) {
        hudShaderProgram.bind();

        //Matrix4f ortho = transformation.getProjectionMatrixOrtho(0, window.getWidth(), window.getHeight(), 0);

        Matrix4f ortho = transformation.getProjectionMatrixOrtho(-16, 16, 16, -16, 0, 16);
        for (GameItem gameItem : hud.getGameItems()) {
            Mesh mesh = gameItem.getMesh();
            Matrix4f projection_matrix = transformation.buildOrtoProjModelMatrix(gameItem, ortho);
            hudShaderProgram.setUniformMat4f("projection_matrix", projection_matrix);
            hudShaderProgram.setUniform("color", gameItem.getMesh().getMaterial().getAmbientColour());
            hudShaderProgram.setUniform("hasTexture", gameItem.getMesh().getMaterial().isTextured() ? 1 : 0);

            // Render the mesh for this HUD item
            mesh.render();
        }

        hudShaderProgram.unbind();
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }

    public void setAmbient_light(Vector4f ambient_light) {
        this.ambient_light = ambient_light;
    }

    public Vector4f getAmbient_light() {
        return ambient_light;
    }

    ;
}
