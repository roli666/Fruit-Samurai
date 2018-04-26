package com.starkindustries.fruitsamurai.GameLogic;

import com.starkindustries.fruitsamurai.Engine.Renderer;
import com.starkindustries.fruitsamurai.Engine.Window;
import com.starkindustries.fruitsamurai.Graphics.GameItem;
import com.starkindustries.fruitsamurai.Graphics.Mesh;
import com.starkindustries.fruitsamurai.Graphics.Texture;
import com.starkindustries.fruitsamurai.Interfaces.IGameLogic;
import com.starkindustries.fruitsamurai.Utils.FileUtils;
import com.starkindustries.fruitsamurai.Utils.OBJLoader;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.List;

public class FruitSamurai implements IGameLogic {
    private int direction = 0;
    private float color = 0.0f;
    private final Renderer renderer;
    private boolean slashing = false;
    private Mesh mesh;
    private List<GameItem> items = new ArrayList<>();

    public FruitSamurai() {
        renderer = new Renderer();
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);
        float[] vertices = new float[]{
        		-10f, 10f, 0f,
        		-10f, -10f, 0f,
        		10f, -10f, 0f,
        		10f, 10f, 0f,
            };
    	int[] indices = new int[] {
        		0,1,2,0,3,2
        	};
    	float[] textcoords = new float[]{
    			1f, 1f,
                1f, 0f,
                0f, 0f,
                0f, 1f,
    		};
    	float[] normals = new float[]{
    			1f,1f,1f,
    		};
    	Texture texture = new Texture(FileUtils.getTexturesFolder()+"\\background_def.jpg");
    	Mesh m_melon = OBJLoader.loadmesh(FileUtils.getMeshesFolder()+"\\melon.obj");
    	Mesh m_background = new Mesh(vertices, indices, textcoords, normals);
    	m_background.setTexture(texture);
    	GameItem melon = new GameItem(m_melon);
    	GameItem background = new GameItem(m_background);
    	items.add(background);
    	//items.add(melon);
    }

    @Override
    public void input(Window window) {
        if ( window.isKeyPressed(GLFW_KEY_UP) ) {
            direction = 1;
        } else if ( window.isKeyPressed(GLFW_KEY_DOWN) ) {
            direction = -1;
        } else {
            direction = 0;
        }
        if(window.isMouseKeyPressed(GLFW_MOUSE_BUTTON_1)){
            slashing = true;
            System.out.println("Slashing at X:" + window.getMouseX() + " Y:" + window.getMouseY());
        }
        if(window.isMouseKeyReleased(GLFW_MOUSE_BUTTON_1) && slashing == true)
        {
            slashing = false;
            System.out.println("Stopped Slashing");
        }
    }

    @Override
    public void update(float interval) {
        color += direction * 0.01f;
        if (color > 1) {
            color = 1.0f;
        } else if ( color < 0 ) {
            color = 0.0f;
        }
    }

    @Override
    public void render(Window window) {
        window.setClearColor(color, color, color, 0.0f);
        renderer.render(window,items);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        for (GameItem gameItem : items) {
            gameItem.getMesh().cleanUp();
        }
    }

}
