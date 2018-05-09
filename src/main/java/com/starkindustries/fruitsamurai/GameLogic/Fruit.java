package com.starkindustries.fruitsamurai.GameLogic;

import com.starkindustries.fruitsamurai.Graphics.GameItem;
import com.starkindustries.fruitsamurai.Graphics.Mesh;
import com.starkindustries.fruitsamurai.Utils.AssimpOBJLoader;
import com.starkindustries.fruitsamurai.Utils.FileUtils;
import org.joml.Vector3f;

public class Fruit extends GameItem {
    private Enums.Fruit fruitType;

    Fruit(Enums.Fruit ft) throws Exception{
        super();
        fruitType = ft;
        super.menuItem = false;
        setScale(2);
        Mesh [] mesh = new Mesh[]{};

        switch (ft)
        {
            case Apple:
                mesh = AssimpOBJLoader.load(FileUtils.getMeshesFolder()+"apple.obj");
                break;
            case Melon:
                mesh = AssimpOBJLoader.load(FileUtils.getMeshesFolder()+"melon.obj");
                break;
            case Orange:
                mesh = AssimpOBJLoader.load(FileUtils.getMeshesFolder()+"orange.obj");
                break;
        }
        setMesh(mesh);
    }
}
