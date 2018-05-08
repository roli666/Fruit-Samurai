package com.starkindustries.fruitsamurai.GameLogic;

import com.starkindustries.fruitsamurai.Graphics.GameItem;
import com.starkindustries.fruitsamurai.Graphics.Mesh;
import com.starkindustries.fruitsamurai.Utils.AssimpOBJLoader;
import com.starkindustries.fruitsamurai.Utils.FileUtils;

public class Fruit extends GameItem {
    private Enums.Fruit fruitType;
    Mesh [] mesh;
    GameItem gameItem;

    Fruit(Enums.Fruit ft) throws Exception{
        super();
        fruitType = ft;
        super.menuItem = false;
        setScale(2);
        Mesh [] mesh = new Mesh[]{};
        switch (ft)
        {
            case Apple:
                mesh = AssimpOBJLoader.load(FileUtils.getMeshesFolder()+"melon.obj");
                break;
            case Melon:
                mesh = AssimpOBJLoader.load(FileUtils.getMeshesFolder()+"melon.obj");
                break;
            case Banana:
                mesh = AssimpOBJLoader.load(FileUtils.getMeshesFolder()+"melon.obj");
                break;
        }
        this.mesh = mesh;
        gameItem = new GameItem(mesh);
    }
}
