package com.starkindustries.fruitsamurai.Utils;

import com.starkindustries.fruitsamurai.Graphics.Material;
import com.starkindustries.fruitsamurai.Graphics.Mesh;
import com.starkindustries.fruitsamurai.Graphics.Texture;
import org.apache.commons.io.IOUtils;
import org.joml.Vector4f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.assimp.Assimp.*;
/**
 * This class is used to load complex Models into the Game.
 * The AI objects use the {@link Assimp} class for more information check the official <a href="https://github.com/assimp/assimp">Assimp</a> website.
 * Deprecated class since I couldn't import OBJ files from InputStream correctly.
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
@Deprecated
public class AssimpOBJLoader {
    private static final Logger logger = LoggerFactory.getLogger(AssimpOBJLoader.class.getName());
    /**
     * Loads a file from the provided resource path.
     * @param resourcePath
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @throws Exception
     * @return a {@link Mesh} array
     */
    public static Mesh[] load(String resourcePath) throws Exception {
        return load(resourcePath, aiProcess_JoinIdenticalVertices | aiProcess_Triangulate | aiProcess_FixInfacingNormals);
    }
    /**
     * Loads a file from the provided resource path.
     * @param resourcePath
     * @param flags AssimpFlags
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @throws Exception
     * @return a {@link Mesh} array
     */
    public static Mesh[] load(String resourcePath, int flags) throws Exception {
        logger.debug("Res path: {}",resourcePath);
        byte[] bytes = IOUtils.toByteArray(AssimpOBJLoader.class.getResourceAsStream(resourcePath));
        ByteBuffer bb = BufferUtils.createByteBuffer(BufferUtils.null_terminate_byte_array(bytes));
        AIScene aiScene = aiImportFile(bb, flags);
        if (aiScene == null) {
            throw new IOException("Error loading model: " + aiGetErrorString());
        }
        int numMaterials = aiScene.mNumMaterials();
        PointerBuffer aiMaterials = aiScene.mMaterials();
        List<Material> materials = new ArrayList<>();
        for (int i = 0; i < numMaterials; i++) {
            AIMaterial aiMaterial = AIMaterial.create(aiMaterials.get(i));
            processMaterial(aiMaterial, materials);
        }
        int numMeshes = aiScene.mNumMeshes();
        PointerBuffer aiMeshes = aiScene.mMeshes();
        Mesh[] meshes = new Mesh[numMeshes];
        for (int i = 0; i < numMeshes; i++) {
            AIMesh aiMesh = AIMesh.create(aiMeshes.get(i));
            Mesh mesh = processMesh(aiMesh, materials);
            meshes[i] = mesh;
        }
        return meshes;
    }
    /**
     * Processes the material that came with the resource.
     * @param aiMaterial
     * @param materials a list of {@link Material} objects
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @throws Exception
     */
    private static void processMaterial(AIMaterial aiMaterial, List<Material> materials) throws Exception {
        AIColor4D colour = AIColor4D.create();
        AIString path = AIString.calloc();
        aiGetMaterialTexture(aiMaterial, aiTextureType_DIFFUSE, 0, path, (IntBuffer) null, null, null, null, null, null);
        String textPath = path.dataString();
        Texture texture = null;
        if (textPath != null && textPath.length() > 0) {
            TextureCache textCache = TextureCache.getInstance();
            texture = textCache.getTexture(textPath);
        }
        Vector4f ambient = Material.DEFAULT_COLOUR;
        int result = aiGetMaterialColor(aiMaterial, AI_MATKEY_COLOR_AMBIENT, aiTextureType_NONE, 0, colour);
        if (result == 0) {
            ambient = new Vector4f(colour.r(), colour.g(), colour.b(), colour.a());
        }
        Vector4f diffuse = Material.DEFAULT_COLOUR;
        result = aiGetMaterialColor(aiMaterial, AI_MATKEY_COLOR_DIFFUSE, aiTextureType_NONE, 0, colour);
        if (result == 0) {
            diffuse = new Vector4f(colour.r(), colour.g(), colour.b(), colour.a());
        }
        Vector4f specular = Material.DEFAULT_COLOUR;
        result = aiGetMaterialColor(aiMaterial, AI_MATKEY_COLOR_SPECULAR, aiTextureType_NONE, 0, colour);
        if (result == 0) {
            specular = new Vector4f(colour.r(), colour.g(), colour.b(), colour.a());
        }
        Material material = new Material(ambient, diffuse, specular, 1.0f);
        material.setTexture(texture);
        materials.add(material);
    }
    /**
     * Processes the meshes that came with the resource.
     * @param aiMesh
     * @param materials a list of {@link Material} objects
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @return {@link Mesh}
     */
    private static Mesh processMesh(AIMesh aiMesh, List<Material> materials) {
        List<Float> vertices = new ArrayList<>();
        List<Float> textures = new ArrayList<>();
        List<Float> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList();
        processVertices(aiMesh, vertices);
        processNormals(aiMesh, normals);
        processTextCoords(aiMesh, textures);
        processIndices(aiMesh, indices);
        Mesh mesh = new Mesh(
                ListUtils.listToFloatArray(vertices),
                ListUtils.listIntToArray(indices),
                ListUtils.listToFloatArray(textures),
                ListUtils.listToFloatArray(normals)
        );
        Material material;
        int materialIdx = aiMesh.mMaterialIndex();
        if (materialIdx >= 0 && materialIdx < materials.size()) {
            material = materials.get(materialIdx);
        } else {
            material = new Material();
        }
        mesh.setMaterial(material);
        return mesh;
    }
    /**
     * Processes the vertices in a mesh.
     * @param aiMesh
     * @param vertices a list of floats
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    private static void processVertices(AIMesh aiMesh, List<Float> vertices) {
        AIVector3D.Buffer aiVertices = aiMesh.mVertices();
        while (aiVertices.remaining() > 0) {
            AIVector3D aiVertex = aiVertices.get();
            vertices.add(aiVertex.x());
            vertices.add(aiVertex.y());
            vertices.add(aiVertex.z());
        }
    }
    /**
     * Processes the normals in a mesh.
     * @param aiMesh
     * @param normals a list of floats
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    private static void processNormals(AIMesh aiMesh, List<Float> normals) {
        AIVector3D.Buffer aiNormals = aiMesh.mNormals();
        while (aiNormals != null && aiNormals.remaining() > 0) {
            AIVector3D aiNormal = aiNormals.get();
            normals.add(aiNormal.x());
            normals.add(aiNormal.y());
            normals.add(aiNormal.z());
        }
    }
    /**
     * Processes the textures in a mesh.
     * @param aiMesh
     * @param textures a list of floats
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    private static void processTextCoords(AIMesh aiMesh, List<Float> textures) {
        AIVector3D.Buffer textCoords = aiMesh.mTextureCoords(0);
        int numTextCoords = textCoords != null ? textCoords.remaining() : 0;
        for (int i = 0; i < numTextCoords; i++) {
            AIVector3D textCoord = textCoords.get();
            textures.add(textCoord.x());
            textures.add(1 - textCoord.y());
        }
    }
    /**
     * Processes the indices in a mesh.
     * @param aiMesh
     * @param indices a list of ints
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    private static void processIndices(AIMesh aiMesh, List<Integer> indices) {
        int numFaces = aiMesh.mNumFaces();
        AIFace.Buffer aiFaces = aiMesh.mFaces();
        for (int i = 0; i < numFaces; i++) {
            AIFace aiFace = aiFaces.get(i);
            IntBuffer buffer = aiFace.mIndices();
            while (buffer.remaining() > 0) {
                indices.add(buffer.get());
            }
        }
    }

}
