package com.starkindustries.fruitsamurai.GameLogic;

import com.starkindustries.fruitsamurai.Graphics.GameItem;
import com.starkindustries.fruitsamurai.Graphics.Material;
import com.starkindustries.fruitsamurai.Graphics.Mesh;
import com.starkindustries.fruitsamurai.Graphics.Texture;
import com.starkindustries.fruitsamurai.Utils.FileUtils;
import com.starkindustries.fruitsamurai.Utils.OBJLoader;
import org.joml.Vector3f;

/**
 * This class represents the background game item.
 * This class is a subclass of {@link GameItem}
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class Background extends GameItem {

    /**
     * Standard constructor of the {@link Background} class.
     * @param background An enumeration to set the desired background texture.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @throws Exception
     */
    Background(Enums.Background background) throws Exception{
        Mesh mesh = OBJLoader.loadmesh("/meshes/background.obj");
        Texture t_background = new Texture("/textures/background_def.jpg");
        menuItem = false;
        switch (background)
        {
            case DEFAULT:
                t_background = new Texture("/textures/background_def.jpg");
                break;
            case SAMURAI:
                t_background = new Texture("/textures/background_2.jpg");
                break;
        }
        mesh.setMaterial(new Material());
        mesh.getMaterial().setTexture(t_background);
        setMesh(mesh);
    }
    /**
     * Sets the background texture.
     * @param background An enumeration to set the desired background texture.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @throws Exception
     */
    public void setBackground(Enums.Background background) throws  Exception{
        switch (background)
        {
            case DEFAULT:
                getMesh().getMaterial().setTexture(new Texture("/textures/background_def.jpg"));
                break;
            case SAMURAI:
                getMesh().getMaterial().setTexture(new Texture("/textures/background_2.jpg"));
                break;
        }
    }
}
