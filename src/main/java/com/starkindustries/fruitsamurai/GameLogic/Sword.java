package com.starkindustries.fruitsamurai.GameLogic;

import com.starkindustries.fruitsamurai.Graphics.GameItem;
import com.starkindustries.fruitsamurai.Graphics.Material;
import com.starkindustries.fruitsamurai.Graphics.Mesh;
import com.starkindustries.fruitsamurai.Graphics.Texture;
import com.starkindustries.fruitsamurai.Utils.FileUtils;
import com.starkindustries.fruitsamurai.Utils.OBJLoader;

public class Sword extends GameItem {
    Texture t_sword;
    public boolean slashing;

    Sword(Enums.Sword st) throws Exception{
        super();
        Mesh mesh = OBJLoader.loadmesh(FileUtils.getMeshesFolder()+"sword.obj");
        slashing = false;
        super.visible = false;
        switch (st) {
            case Glow:
                t_sword = new Texture(FileUtils.getTexturesFolder() + "sword.png");
                break;
        }
        mesh.setMaterial(new Material());
        mesh.getMaterial().setTexture(t_sword);
        setMesh(mesh);
    }
}
