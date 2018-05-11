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
 * This class represents a Shader.
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class Shader {
    private final int ID;
    private int vertexShaderId;
    private int fragmentShaderId;
    private Map<String, Integer> uniforms;
    /**
     * Standard constructor of the {@link Shader} class.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @throws Exception
     */
    public Shader() throws Exception {
        ID = glCreateProgram();
        uniforms = new HashMap<String, Integer>();
        if (ID == 0)
            throw new Exception("Could not create Shader");
    }
    /**
     * Creates a vertex shader.
     * @param shaderCode
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @throws Exception
     */
    public void createVertexShader(String shaderCode) throws Exception {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
    }
    /**
     * Creates a fragment shader.
     * @param shaderCode
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @throws Exception
     */
    public void createFragmentShader(String shaderCode) throws Exception {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }
    /**
     * Creates a shader.
     * @param shaderCode
     * @param shaderType
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @throws Exception
     * @return shader identifier
     */
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
    /**
     * Links a shader.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @throws Exception
     */
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
    /**
     * Binds the shader program.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void bind() {
        glUseProgram(ID);
    }
    /**
     * Unbinds the shader program.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void unbind() {
        glUseProgram(0);
    }

    /**
     * Creates a uniform the shader program can use.
     * @param name
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @return location of the uniform(ID)
     */
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
    /**
     * Creates a uniform {@link Material} the shader program can use.
     * @param uniformName name of the uniform
     * @param material a standard {@link Material} object
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void setUniform(String uniformName, Material material) {
        setUniform(uniformName + ".ambient", material.getAmbientColour());
        setUniform(uniformName + ".diffuse", material.getDiffuseColour());
        setUniform(uniformName + ".specular", material.getSpecularColour());
        setUniform(uniformName + ".hasTexture", material.isTextured() ? 1 : 0);
        setUniform(uniformName + ".hasNormalMap", material.hasNormalMap() ? 1 : 0);
        setUniform(uniformName + ".reflectance", material.getReflectance());
    }
    /**
     * Creates a uniform integer the shader program can use.
     * @param uniformName name of the uniform
     * @param value int
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void setUniform(String uniformName, int value) {
        glUniform1i(uniforms.get(uniformName), value);
    }
    /**
     * Creates a uniform float the shader program can use.
     * @param uniformName name of the uniform
     * @param value float
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void setUniform(String uniformName, float value) {
        glUniform1f(uniforms.get(uniformName), value);
    }
    /**
     * Creates a uniform {@link Vector4f} the shader program can use.
     * @param uniformName name of the uniform
     * @param value a {@link Vector4f} object
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void setUniform(String uniformName, Vector4f value) {
        glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w);
    }
    /**
     * Creates a uniform integer the shader program can use.
     * @param name name of the uniform
     * @param value int
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void setUniform1i(String name, int value) {
        glUniform1i(createUniform(name), value);
    }
    /**
     * Creates a uniform float the shader program can use.
     * @param name name of the uniform
     * @param value float
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void setUniform1f(String name, float value) {
        glUniform1f(createUniform(name), value);
    }
    /**
     * Creates a uniform 2d vector the shader program can use.
     * @param name name of the uniform
     * @param x float
     * @param y float
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void setUniform2f(String name, float x, float y) {
        glUniform2f(createUniform(name), x, y);
    }
    /**
     * Creates a uniform {@link Vector3f} the shader program can use.
     * @param name name of the uniform
     * @param vec a {@link Vector3f} object
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void setUniform3f(String name, Vector3f vec) {
        glUniform3f(createUniform(name), vec.x, vec.y, vec.z);
    }
    /**
     * Creates a uniform {@link Vector4f} the shader program can use.
     * @param name name of the uniform
     * @param vec a {@link Vector4f} object
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void setUniform4f(String name, Vector4f vec) {
        glUniform4f(createUniform(name), vec.x, vec.y, vec.z, vec.w);
    }
    /**
     * Creates a uniform {@link Matrix4f} the shader program can use.
     * @param name name of the uniform
     * @param mat a {@link Matrix4f} object
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void setUniformMat4f(String name, Matrix4f mat) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            mat.get(fb);
            glUniformMatrix4fv(createUniform(name), false, fb);
        }
    }
    /**
     * This method cleans up the shader.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void cleanup() {
        unbind();
        if (ID != 0) {
            glDeleteProgram(ID);
        }
    }

}
