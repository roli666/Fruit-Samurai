/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starkindustries.fruitsamurai.Graphics;


import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL20.*;

/**
 * @author Raóland
 */
public class Shader {
    private final int ID;
    private int vertexShaderId;
    private int fragmentShaderId;

    private Map<String, Integer> uniforms;

    public Shader() throws Exception {
        ID = glCreateProgram();
        uniforms = new HashMap<String, Integer>();
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

    public int createUniform(String name) {
        if (uniforms.containsKey(name)) {
            return uniforms.get(name);
        }
        int uniformlocation = glGetUniformLocation(ID, name);
        if (uniformlocation == -1) {
            System.err.println("Could not find uniform variable '" + name + "' .");
        } else {
            uniforms.put(name, uniformlocation);
        }
        return uniformlocation;
    }

    public void setUniform(String uniformName, Material material) {
        setUniform(uniformName + ".ambient", material.getAmbientColour());
        setUniform(uniformName + ".diffuse", material.getDiffuseColour());
        setUniform(uniformName + ".specular", material.getSpecularColour());
        setUniform(uniformName + ".hasTexture", material.isTextured() ? 1 : 0);
        setUniform(uniformName + ".hasNormalMap", material.hasNormalMap() ? 1 : 0);
        setUniform(uniformName + ".reflectance", material.getReflectance());
    }
    public void setUniform(String uniformName, int value) {
        glUniform1i(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, float value) {
        glUniform1f(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, Vector4f value) {
        glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w);
    }

    public void setUniform1i(String name, int value) {
        glUniform1i(createUniform(name), value);
    }

    public void setUniform1f(String name, float value) {
        glUniform1f(createUniform(name), value);
    }

    public void setUniform2f(String name, float x, float y) {
        glUniform2f(createUniform(name), x, y);
    }

    public void setUniform3f(String name, Vector3f vec) {
        glUniform3f(createUniform(name), vec.x, vec.y, vec.z);
    }

    public void setUniform4f(String name, Vector4f vec) {
        glUniform4f(createUniform(name), vec.x, vec.y, vec.z, vec.w);
    }

    public void setUniformMat4f(String name, Matrix4f mat) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            mat.get(fb);
            glUniformMatrix4fv(createUniform(name), false, fb);
        }
    }

    public void cleanup() {
        unbind();
        if (ID != 0) {
            glDeleteProgram(ID);
        }
    }

}
