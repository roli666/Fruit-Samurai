package com.starkindustries.fruitsamurai.GameLogic;

import com.starkindustries.fruitsamurai.Graphics.GameItem;
import com.starkindustries.fruitsamurai.Graphics.Material;
import com.starkindustries.fruitsamurai.Graphics.Mesh;
import com.starkindustries.fruitsamurai.Graphics.Texture;
import com.starkindustries.fruitsamurai.Utils.*;

/**
 * This class represents a fruit game item.
 * This class is a subclass of {@link GameItem}
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class Fruit extends GameItem {
    private Enums.Fruit fruitType;

    /**
     * Standard constructor of the {@link Fruit} class.
     * @param ft An enumeration to set the desired fruit.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @throws Exception
     */
    Fruit(Enums.Fruit ft) throws Exception{
        super();
        fruitType = ft;
        super.menuItem = false;
        setScale(2);
        MeshCache mc = MeshCache.getInstance();
        TextureCache tc = TextureCache.getInstance();
        Mesh melon_mesh = mc.getMesh("/meshes/melon.obj");
        Mesh apple_mesh = mc.getMesh("/meshes/apple.obj");
        Mesh orange_mesh = mc.getMesh("/meshes/orange.obj");
        Texture melon_t = tc.getTexture("/textures/melon_t.png");
        Texture orange_t = tc.getTexture("/textures/orange_t.jpg");
        Texture apple_t = tc.getTexture("/textures/apple_t.png");
        melon_mesh.setMaterial(new Material());
        melon_mesh.getMaterial().setTexture(melon_t);
        orange_mesh.setMaterial(new Material());
        orange_mesh.getMaterial().setTexture(orange_t);
        apple_mesh.setMaterial(new Material());
        apple_mesh.getMaterial().setTexture(apple_t);
        switch (ft)
        {
            case Apple:
                setMesh(apple_mesh);
                break;
            case Melon:
                setMesh(melon_mesh);
                break;
            case Orange:
                setMesh(orange_mesh);
                break;
        }
    }
}
