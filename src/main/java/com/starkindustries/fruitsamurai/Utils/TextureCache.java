package com.starkindustries.fruitsamurai.Utils;

import com.starkindustries.fruitsamurai.Graphics.Texture;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
/**
 * This class caches {@link Texture} objects for better performance.
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class TextureCache {
    private static TextureCache INSTANCE;

    private Map<URL, Texture> texturesMap;

    /**
     * Standard constructor initializes the TextureCache.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    private TextureCache() {
        texturesMap = new HashMap<>();
    }

    /**
     * @return {@link TextureCache} instance.
     */
    public static synchronized TextureCache getInstance() {
        if ( INSTANCE == null ) {
            INSTANCE = new TextureCache();
        }
        return INSTANCE;
    }
    /**
     * @param path path to the texture
     * @return a texture from the given path or from the texture cache if it already exists
     * @throws Exception
     */
    public Texture getTexture(URL path) throws Exception {
        Texture texture = texturesMap.get(path);
        if ( texture == null ) {
            texture = new Texture(path);
            texturesMap.put(path, texture);
        }
        return texture;
    }
}
