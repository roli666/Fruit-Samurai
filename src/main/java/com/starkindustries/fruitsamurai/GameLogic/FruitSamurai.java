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
import com.starkindustries.fruitsamurai.Utils.FileUtils;
import com.starkindustries.fruitsamurai.Utils.OBJLoader;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joml.Matrix3dc;
import org.joml.Matrix3fc;
import org.joml.Matrix3x2fc;
import org.joml.Matrix4dc;
import org.joml.Matrix4fc;
import org.joml.Matrix4x3fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class FruitSamurai implements IGameLogic {
    private int direction = 0;
    private float color = 0.0f;
    private final Renderer renderer;
    private boolean slashing = false;
    private List<GameItem> items = new ArrayList<>();
    private GameItem melon;
    private GameItem background;
    private Mesh m_melon;
    private static DynamicsWorld dynamicsWorld;
    private static Set<RigidBody> bodies = new HashSet<>();
    private static boolean appyForce = false;
    private Timer physicstimer = new Timer();

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
        physicstimer.init();
    	renderer.init(window);
        float[] vertices = new float[]{
        		-10f, 10f, -2f,
        		-10f, -10f, -2f,
        		10f, -10f, -2f,
        		10f, 10f, -2f,
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
    			0f,0f,0f,
    		};
    	Texture background_t = new Texture(FileUtils.getTexturesFolder()+"\\background_def.jpg");
    	Texture melon_t = new Texture(FileUtils.getTexturesFolder()+"\\melon_t.png");
    	m_melon = OBJLoader.loadmesh(FileUtils.getMeshesFolder()+"\\melon.obj");
    	Mesh m_background = new Mesh(vertices, indices, textcoords, normals);
    	m_background.setTexture(background_t);
    	m_melon.setTexture(melon_t);
    	melon = new GameItem(m_melon);
    	background = new GameItem(m_background);
    	items.add(background);
    	melon.affectedByPhysics = false;
    	melon.setAcceleration(new Vector3f(0,0,0));
    	melon.setPosition(-5,0,0);
    	melon.menuItem = true;
    	items.add(melon);
    	
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
        if ( window.isKeyPressed(GLFW_KEY_SPACE) ) {
           	GameItem melone = new GameItem(m_melon);
           	melone.setPosition(0, 10, 0);
           	melone.affectedByPhysics = true;
           	melone.setAcceleration(new Vector3f(0f,-0.5f,0));
           	items.add(melone);
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
