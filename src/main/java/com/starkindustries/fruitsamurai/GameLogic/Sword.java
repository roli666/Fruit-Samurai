package com.starkindustries.fruitsamurai.GameLogic;

import com.starkindustries.fruitsamurai.Graphics.GameItem;
import com.starkindustries.fruitsamurai.Graphics.Material;
import com.starkindustries.fruitsamurai.Graphics.Mesh;
import com.starkindustries.fruitsamurai.Graphics.Texture;
import com.starkindustries.fruitsamurai.Utils.FileUtils;
import com.starkindustries.fruitsamurai.Utils.OBJLoader;
import org.joml.Vector3f;
/**
 * This class represents a sword game item.
 * This class is a subclass of {@link GameItem}
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class Sword extends GameItem {
    Texture t_sword;
    public boolean slashing;
    public Vector3f previousPos;
    /**
     * Standard constructor of the {@link Sword} class.
     * @param st An enumeration to set the desired sword.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    Sword(Enums.Sword st) throws Exception{
        super();
        Mesh mesh = OBJLoader.loadmesh(Fruit.class.getResource("/meshes/sword.obj"));
        slashing = false;
        super.visible = false;
        switch (st) {
            case Glow:
                t_sword = new Texture(Fruit.class.getResource("/textures/sword.png"));
                break;
        }
        previousPos = super.getPosition();
        mesh.setMaterial(new Material());
        mesh.getMaterial().setTexture(t_sword);
        setMesh(mesh);
    }
}
