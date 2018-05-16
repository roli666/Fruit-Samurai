package com.starkindustries.fruitsamurai.Utils;

import com.starkindustries.fruitsamurai.Graphics.Mesh;
import com.starkindustries.fruitsamurai.Graphics.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * This class caches {@link Texture} objects for better performance.
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class MeshCache {
    private static MeshCache INSTANCE;

    private Map<String, Mesh> meshMap;

    /**
     * Standard constructor initializes the TextureCache.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    private MeshCache() {
        meshMap = new HashMap<>();
    }

    /**
     * @return {@link MeshCache} instance.
     */
    public static synchronized MeshCache getInstance() {
        if ( INSTANCE == null ) {
            INSTANCE = new MeshCache();
        }
        return INSTANCE;
    }
    /**
     * @param path path to the texture
     * @return a texture from the given path or from the texture cache if it already exists
     * @throws Exception
     */
    public Mesh getMesh(String path) throws Exception {
        Mesh mesh= meshMap.get(path);
        if ( mesh == null ) {
            mesh = OBJLoader.loadmesh(path);
            meshMap.put(path, mesh);
        }
        return mesh;
    }
}
