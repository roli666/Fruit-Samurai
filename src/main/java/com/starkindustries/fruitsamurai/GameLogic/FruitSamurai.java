package com.starkindustries.fruitsamurai.GameLogic;

import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.starkindustries.fruitsamurai.Engine.Renderer;
import com.starkindustries.fruitsamurai.Engine.Window;
import com.starkindustries.fruitsamurai.Graphics.GameItem;
import com.starkindustries.fruitsamurai.Interfaces.IGameLogic;

import static org.lwjgl.glfw.GLFW.*;

import java.lang.Math;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joml.*;

public class FruitSamurai implements IGameLogic {
    private int direction = 0;
    private float color = 0.0f;
    private final Renderer renderer;
    private List<GameItem> items = new ArrayList<>();
    private static DynamicsWorld dynamicsWorld;
    private static Set<RigidBody> bodies = new HashSet<>();
    private HUD hud;
    private Background background;
    private Fruit fruit;
    private Sword sword;

    public FruitSamurai() {
        renderer = new Renderer();
    }

    @Override
    public void init(Window window) throws Exception {
    	/*BroadphaseInterface broadphase = new DbvtBroadphase();
    	CollisionConfiguration collconf = new DefaultCollisionConfiguration();
    	CollisionDispatcher disp = new CollisionDispatcher(collconf);
    	ConstraintSolver solver = new SequentialImpulseConstraintSolver();
    	dynamicsWorld = new DiscreteDynamicsWorld(disp, broadphase, solver, collconf);
    	dynamicsWorld.setGravity(new javax.vecmath.Vector3f(0,-0.1f,0));*/
    	renderer.init(window);

        fruit = new Fruit(Enums.Fruit.Melon);
        background = new Background(Enums.Background.DEFAULT);
        hud = new HUD("DEMO");
        sword = new Sword(Enums.Sword.Glow);

        fruit.menuItem = true;
        fruit.setPosition(-5,0,0);
        fruit.setScale(2);
        sword.setScale(2);

        items.add(fruit);
        items.add(background);
        items.add(sword);
    }

    @Override
    public void input(Window window) {
        if(window.isMouseKeyPressed(GLFW_MOUSE_BUTTON_1)){
            sword.slashing = true;
            sword.visible = true;
            window.hideMouse();
            sword.setPosition((float) window.getMouseX()/window.getWidth()*2*16-16, (float) window.getMouseY()/window.getHeight()*2*16-16,sword.getPosition().z);
            System.out.println(String.format("Slashing at X:%.2f Y:%.2f",window.getMouseX()/window.getWidth()*2*16-16,window.getMouseY()/window.getHeight()*2*16-16));
        }
        if(window.isMouseKeyReleased(GLFW_MOUSE_BUTTON_1) && sword.slashing == true)
        {
            sword.slashing = false;
            sword.visible = false;
            window.showMouse();
            System.out.println("Stopped Slashing");
        }
        if ( window.isKeyPressed(GLFW_KEY_SPACE) ) {
            try {
                double accelerationY = -1.8 + Math.random() * (-1.5 - (-1.8));
                double accelerationX = -1 + Math.random() * (1 - (-1));
                Fruit new_fruit = new Fruit(Enums.Fruit.Melon);
                new_fruit.setPosition(0, 16, 0);
                new_fruit.affectedByPhysics = true;
                new_fruit.setAcceleration(new Vector3f((float)accelerationX,(float)accelerationY,0));
                items.add(new_fruit);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if ( window.isKeyPressed(GLFW_KEY_DOWN) ) {
            Vector4f asd = renderer.getAmbient_light().add(-0.01f,-0.01f,-0.01f,0);
            renderer.setAmbient_light(asd);
        }
        if ( window.isKeyPressed(GLFW_KEY_UP) ) {
            Vector4f asd = renderer.getAmbient_light().add(0.01f,0.01f,0.01f,0);
            renderer.setAmbient_light(asd);
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
        for(GameItem item : items) {
        	if(item.menuItem) {
        		float rotation = item.getRotation().x + 1f;
            	if ( rotation > 360 ) {
            	rotation = 0;
            	}
            	item.setRotation(rotation, rotation, rotation);
        	}
            if(item.affectedByPhysics)
            {
                Vector3f pos = item.getPosition();
                Vector3f acc = item.getAcceleration();
                float gravity = 0.05f;
                acc = new Vector3f(acc.x,acc.y+gravity,acc.z);
                item.setAcceleration(acc);

            	if(pos.x+item.getScale()>=16 || pos.x-item.getScale()<=-16)
            	    item.setAcceleration(-acc.x,acc.y,acc.z);

                item.setPosition(pos.add(acc));

            	float rotation = item.getRotation().x + 5f;
            	if ( rotation > 360 )
            	{
            	    rotation = 0;
            	}
            	item.setRotation(rotation, rotation, rotation);
            }
        }
    }

    @Override
    public void render(Window window) {
        window.setClearColor(color, color, color, 0.0f);
        hud.updateSize(window);
        renderer.render(window,items,hud);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        items.parallelStream().filter(f->f.getMesh()!=null).forEach(a->a.getMesh().cleanUp());
        hud.cleanup();
    }
}
