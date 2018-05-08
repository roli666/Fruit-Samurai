package com.starkindustries.fruitsamurai.GameLogic;

import com.starkindustries.fruitsamurai.Graphics.GameItem;
import com.starkindustries.fruitsamurai.Graphics.Material;
import com.starkindustries.fruitsamurai.Graphics.Mesh;
import com.starkindustries.fruitsamurai.Graphics.Texture;
import com.starkindustries.fruitsamurai.Utils.FileUtils;
import com.starkindustries.fruitsamurai.Utils.OBJLoader;
import org.joml.Vector3f;


public class Background extends GameItem {
    Mesh mesh;
    Texture t_background;
    GameItem gameItem;

    Background(Enums.Background background) throws Exception{
        Mesh mesh = OBJLoader.loadmesh(FileUtils.getMeshesFolder()+"background.obj");
        menuItem = true;
        switch (background)
        {
            case DEFAULT:
                t_background = new Texture(FileUtils.getTexturesFolder()+"background_def.jpg");
                break;
            case SAMURAI:
                t_background = new Texture(FileUtils.getTexturesFolder()+"background_2.jpg");
                break;
        }
        mesh.setMaterial(new Material());
        mesh.getMaterial().setTexture(t_background);
        gameItem = new GameItem(mesh);
    }

    public void setBackground(Enums.Background background) throws  Exception{
        switch (background)
        {
            case DEFAULT:
                t_background = new Texture(FileUtils.getTexturesFolder()+"background_def.jpg");
                break;
            case SAMURAI:
                t_background = new Texture(FileUtils.getTexturesFolder()+"background_2.jpg");
                break;
        }
        mesh.getMaterial().setTexture(t_background);
    }
}
