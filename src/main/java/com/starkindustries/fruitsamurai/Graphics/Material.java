package com.starkindustries.fruitsamurai.Graphics;

import org.joml.Vector4f;
/**
 * This class represents a material applied to a {@link Mesh}.
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class Material {

    public static final Vector4f DEFAULT_COLOUR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

    private Vector4f ambientColour;

    private Vector4f diffuseColour;

    private Vector4f specularColour;

    private float reflectance;

    private Texture texture;

    private Texture normalMap;
    /**
     * Standard constructor of the {@link Material} class.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public Material() {
        this.ambientColour = DEFAULT_COLOUR;
        this.diffuseColour = DEFAULT_COLOUR;
        this.specularColour = DEFAULT_COLOUR;
        this.texture = null;
        this.reflectance = 0;
    }

    /**
     * Reflectance constructor of the {@link Material} class.
     * @param colour
     * @param reflectance
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public Material(Vector4f colour, float reflectance) {
        this(colour, colour, colour, null, reflectance);
    }
    /**
     * Texture constructor of the {@link Material} class.
     * @param texture
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public Material(Texture texture) {
        this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, texture, 0);
    }
    /**
     * Texture and Reflectance constructor of the {@link Material} class.
     * @param texture
     * @param reflectance
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public Material(Texture texture, float reflectance) {
        this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, texture, reflectance);
    }
    /**
     * Standard Textureless Material constructor of the {@link Material} class.
     * @param ambientColour
     * @param diffuseColour
     * @param specularColour
     * @param reflectance
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public Material(Vector4f ambientColour, Vector4f diffuseColour, Vector4f specularColour, float reflectance) {
        this(ambientColour, diffuseColour, specularColour, null, reflectance);
    }
    /**
     * Standard constructor of the {@link Material} class.
     * @param ambientColour
     * @param diffuseColour
     * @param specularColour
     * @param texture
     * @param reflectance
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public Material(Vector4f ambientColour, Vector4f diffuseColour, Vector4f specularColour, Texture texture, float reflectance) {
        this.ambientColour = ambientColour;
        this.diffuseColour = diffuseColour;
        this.specularColour = specularColour;
        this.texture = texture;
        this.reflectance = reflectance;
    }

    /**
     * @return the ambient colour.
     */
    public Vector4f getAmbientColour() {
        return ambientColour;
    }
    /**
     * @param ambientColour sets the ambient colour.
     */
    public void setAmbientColour(Vector4f ambientColour) {
        this.ambientColour = ambientColour;
    }
    /**
     * @return the diffuse colour.
     */
    public Vector4f getDiffuseColour() {
        return diffuseColour;
    }
    /**
     * @param diffuseColour sets the diffuse colour.
     */
    public void setDiffuseColour(Vector4f diffuseColour) {
        this.diffuseColour = diffuseColour;
    }
    /**
     * @return the specular colour.
     */
    public Vector4f getSpecularColour() {
        return specularColour;
    }
    /**
     * @param specularColour sets the specular colour.
     */
    public void setSpecularColour(Vector4f specularColour) {
        this.specularColour = specularColour;
    }
    /**
     * @return the reflectance.
     */
    public float getReflectance() {
        return reflectance;
    }
    /**
     * @param reflectance sets the reflectance.
     */
    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }
    /**
     * @return whether the material has a texture or not.
     */
    public boolean isTextured() {
        return this.texture != null;
    }
    /**
     * @return the {@link Texture} object.
     */
    public Texture getTexture() {
        return texture;
    }
    /**
     * @param texture sets the {@link Texture}.
     */
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    /**
     * @return whether the material has a normal map {@link Texture} or not.
     */
    public boolean hasNormalMap() {
        return this.normalMap != null;
    }
    /**
     * @return the normal map {@link Texture} object.
     */
    public Texture getNormalMap() {
        return normalMap;
    }
    /**
     * @param normalMap sets the normal map {@link Texture}.
     */
    public void setNormalMap(Texture normalMap) {
        this.normalMap = normalMap;
    }
}