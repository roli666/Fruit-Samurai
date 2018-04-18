/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template shaders, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starkindustries.fruitsamurai.Meshes;

import com.starkindustries.fruitsamurai.GameLogic.Enums;
import com.starkindustries.fruitsamurai.Graphics.Shader;
import com.starkindustries.fruitsamurai.Graphics.Texture;
import com.starkindustries.fruitsamurai.Graphics.VertexArray;
import com.starkindustries.fruitsamurai.Utils.FileUtils;

/**
 *
 * @author Raóland
 */
public class Level {
    private VertexArray backgroundvao;
    private Enums.Mode mode;
    private Enums.Background background = Enums.Background.DEFAULT;

    private Texture bgtexture;

    public Level(Enums.Mode mode)
    {
        this.mode = mode;
        float[] vertices = new float[]{
        		-10f, 10f, 0.0f,
        		-10f, -10f, 0.0f,
        		10f, 10f, 0.0f,
        		10f, 10f, 0.0f,
        		-10f, -10f, 0.0f,
        		10f, -10f, 0.0f,
        };
        
        byte[] indices = new byte[]{
        		0, 1, 3, 3, 1, 2,
        };
        
        float[] tcs = new float[]{
            0,0, //upper left
            0,0.5f, //lower left
            1,0.5f, //lower right
            1,0,  //upper right
        };
        backgroundvao = new VertexArray(vertices,indices,tcs);
        switch(background)
        {
            case DEFAULT:
                bgtexture = new Texture(FileUtils.getTexturesFolder()+"\\background_def.jpg");
                break;
            case SAMURAI:
                bgtexture = new Texture(FileUtils.getTexturesFolder()+"\\background_def.jpg");
                break;
            default:
                bgtexture = new Texture(FileUtils.getTexturesFolder()+"\\background_def.jpg");
                break;
        }
    }
    public void setBackground(Enums.Background bg)
    {
        background = bg;
    }

    public void render()
    {
        bgtexture.bind();

        backgroundvao.render();

        bgtexture.unbind();
    }
}
