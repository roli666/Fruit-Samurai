package com.starkindustries.fruitsamurai.GameLogic;

import com.starkindustries.fruitsamurai.Engine.Renderer;
import com.starkindustries.fruitsamurai.Engine.Window;
import com.starkindustries.fruitsamurai.Graphics.GameItem;
import com.starkindustries.fruitsamurai.Graphics.Mesh;
import com.starkindustries.fruitsamurai.Interfaces.IGameLogic;

import static org.lwjgl.glfw.GLFW.*;

public class FruitSamurai implements IGameLogic {
    private int direction = 0;
    private float color = 0.0f;
    private final Renderer renderer;
    private boolean slashing = false;
    private Mesh mesh;

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
    	float[] colors = new float[]{
    			0.5f, 0.0f, 0.0f,
    			0.0f, 0.5f, 0.0f,
    			0.0f, 0.0f, 0.5f,
    			0.0f, 0.5f, 0.5f,
    			};
    	mesh = new Mesh(vertices,colors,indices);
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
    public void render(Window window,GameItem[] items) {
        window.setClearColor(color, color, color, 0.0f);
        renderer.render(window,items);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
    }

}
