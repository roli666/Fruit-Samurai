package com.starkindustries.fruitsamurai.GameLogic;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.starkindustries.fruitsamurai.Engine.Renderer;
import com.starkindustries.fruitsamurai.Engine.Timer;
import com.starkindustries.fruitsamurai.Engine.Window;
import com.starkindustries.fruitsamurai.Graphics.GameItem;
import com.starkindustries.fruitsamurai.Graphics.Mesh;
import com.starkindustries.fruitsamurai.Graphics.Texture;
import com.starkindustries.fruitsamurai.Interfaces.IGameLogic;
import com.starkindustries.fruitsamurai.Utils.AssimpOBJLoader;
import com.starkindustries.fruitsamurai.Utils.FileUtils;

import static org.lwjgl.glfw.GLFW.*;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joml.*;

public class FruitSamurai implements IGameLogic {
    private int direction = 0;
    private float color = 0.0f;
    private final Renderer renderer;
    private boolean slashing = false;
    private List<GameItem> items = new ArrayList<>();
    private GameItem melon;
    private GameItem background;
    private GameItem sword;
    private Mesh[] m_melon;
    private Mesh[] m_background;
    private Mesh[] m_sword;
    private static DynamicsWorld dynamicsWorld;
    private static Set<RigidBody> bodies = new HashSet<>();

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
    	m_melon = AssimpOBJLoader.load(FileUtils.getMeshesFolder()+"melon.obj");
        m_background = AssimpOBJLoader.load(FileUtils.getMeshesFolder()+"background.obj");
        m_sword = AssimpOBJLoader.load(FileUtils.getMeshesFolder()+"sword.obj");
    	melon = new GameItem(m_melon);
    	background = new GameItem(m_background);
        sword = new GameItem(m_sword);
        sword.visible = false;
    	melon.setAcceleration(new Vector3f(0,0,0));
    	melon.setPosition(-5,0,0);
    	melon.menuItem = true;
    	melon.setScale(2);
        items.add(background);
    	items.add(melon);
        items.add(sword);
    	
    }

    @Override
    public void input(Window window) {


        if(window.isMouseKeyPressed(GLFW_MOUSE_BUTTON_1)){
            slashing = true;
            sword.visible = true;
            window.hideMouse();
            sword.setPosition((float) window.getMouseX()/window.getWidth()*2*16-16, (float) window.getMouseY()/window.getHeight()*2*16-16,sword.getPosition().z);
            System.out.println(String.format("Slashing at X:%.2f Y:%.2f",window.getMouseX()/window.getWidth()*2*10-10,window.getMouseY()/window.getHeight()*2*10-10));
        }
        if(window.isMouseKeyReleased(GLFW_MOUSE_BUTTON_1) && slashing == true)
        {
            slashing = false;
            sword.visible = false;
            window.showMouse();
            System.out.println("Stopped Slashing");
        }
        if ( window.isKeyPressed(GLFW_KEY_SPACE) ) {
           	GameItem melone = new GameItem(m_melon);
           	melone.setPosition(0, 10, 0);
           	melone.affectedByPhysics = true;
           	melone.setAcceleration(new Vector3f(0f,-0.5f,0));
           	items.add(melone);
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
            	item.setPosition(item.getPosition().add(item.getAcceleration()));
            	if(item.getAcceleration().y<=item.getAcceleration().y%1)
            		item.setAcceleration(new Vector3f(0,item.getAcceleration().y+0.01f,0));
            	float rotation = item.getRotation().x + 5f;
            	if ( rotation > 360 ) {
            	rotation = 0;
            	}
            	item.setRotation(rotation, rotation, rotation);
            }
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
