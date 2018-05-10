package com.starkindustries.fruitsamurai.GameLogic;

import com.starkindustries.fruitsamurai.Engine.Renderer;
import com.starkindustries.fruitsamurai.Engine.Window;
import com.starkindustries.fruitsamurai.Graphics.GameItem;
import com.starkindustries.fruitsamurai.Graphics.Hud;
import com.starkindustries.fruitsamurai.Interfaces.IGameLogic;

import static org.lwjgl.glfw.GLFW.*;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.starkindustries.fruitsamurai.Utils.RandomUtils;
import org.joml.*;

public class FruitSamurai implements IGameLogic {
    private int direction = 0;
    private float color = 0.0f;
    private final Renderer renderer;
    private List<GameItem> items = new ArrayList<>();
    private Background background;
    private Sword sword;
    private Player player = new Player();
    private int secondsPassed;
    private float intervalBuffer;
    private Hud hud = new Hud();

    public FruitSamurai() {
        renderer = new Renderer();
    }

    @Override
    public void init(Window window) throws Exception {
    	renderer.init(window);
        hud.init(window);

        Fruit start_melon = new Fruit(Enums.Fruit.Melon);
        Fruit leaderboard_apple = new Fruit(Enums.Fruit.Apple);
        Fruit exit_orange = new Fruit(Enums.Fruit.Orange);

        background = new Background(Enums.Background.DEFAULT);
        sword = new Sword(Enums.Sword.Glow);

        start_melon.menuItem = true;
        start_melon.setPosition(-10,0,0);
        start_melon.setScale(2);
        start_melon.setStartsGame(true);
        leaderboard_apple.menuItem = true;
        leaderboard_apple.setPosition(-1,-5,0);
        leaderboard_apple.setScale(2);
        leaderboard_apple.setShowsLeaderboards(true);
        exit_orange.menuItem = true;
        exit_orange.setPosition(8,0,0);
        exit_orange.setScale(2);
        exit_orange.setExitsGame(true);
        sword.setScale(2);

        items.add(start_melon);
        items.add(leaderboard_apple);
        items.add(exit_orange);
        items.add(background);
        items.add(sword);
    }

    @Override
    public void input(Window window) {
        if(window.isMouseKeyPressed(GLFW_MOUSE_BUTTON_1)){
            sword.slashing = true;
            sword.visible = true;
            window.hideMouse();
            Vector3f newPosition = new Vector3f((float) window.getMouseX()/window.getWidth()*2*16-16, (float) window.getMouseY()/window.getHeight()*2*16-16,sword.getPosition().z);
            sword.setPosition(newPosition);
            //float velocity = (newPosition.sub(sword.previousPos).length()*secondsPassed);
            sword.previousPos = newPosition;
            //System.out.println(velocity);
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
                double accelerationY = RandomUtils.floatNumberBetween(-0.8,-0.6);
                double accelerationX = RandomUtils.floatNumberBetween(-0.2,0.2);
                Enums.RandomEnum<Enums.Fruit> r = new Enums.RandomEnum<>(Enums.Fruit.class);
                Fruit new_fruit = new Fruit(r.random());
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
    public void update(float interval,Window window) {
        intervalBuffer += interval;
        if(intervalBuffer>=1) {
            intervalBuffer = 0;
            secondsPassed++;
        }
        window.setElapsedTime(secondsPassed);
        color += direction * 0.01f;
        if (color > 1) {
            color = 1.0f;
        } else if ( color < 0 ) {
            color = 0.0f;
        }
        for(GameItem item : items) {
            Vector3f swordpos = sword.getPosition();
            Vector3f pos = item.getPosition();
            Vector3f acc = item.getAcceleration();
            boolean hitFruit = Math.pow(swordpos.x-pos.x,2)+Math.pow(swordpos.y-pos.y,2)+Math.pow(swordpos.z-pos.z,2)<=Math.pow(item.getScale()*0.6+sword.getScale()*0.6,2);
        	if(item.menuItem) {
            	//Start game
                if(hitFruit && sword.visible && item.isStartsGame() && item.visible) {
                    player.setPlaying(true);
                    startGame();
                }
                //Show LeaderBoards
                if(hitFruit && sword.visible && item.isShowsLeaderboards() && item.visible)
                    showLeaderboards();
                //Exit game
                if(hitFruit && sword.visible && item.isExitsGame() && item.visible)
                    exit(window);
        	}
            if(item.affectedByPhysics)
            {
                float gravity = 0.01f;
                acc = new Vector3f(acc.x,acc.y+gravity,acc.z);
                item.setAcceleration(acc);

            	if(pos.x+item.getScale()>=16 || pos.x-item.getScale()<=-16)
            	    item.setAcceleration(-acc.x,acc.y,acc.z);

                if(pos.y>25) {
                    player.setLives(player.getLives()-1);
                    item.setToDelete(true);
                }
                if(hitFruit && sword.visible) {
                    player.setScore(player.getScore()+1);
                    item.setToDelete(true);
                }

                item.setPosition(pos.add(acc));
            }
            float rotation = item.getRotation().x + 1f;
            if ( rotation > 360 )
            {
                rotation = 0;
            }
            if(item.affectedByPhysics || item.menuItem)
                item.setRotation(rotation, rotation ,rotation);
        }
        if(player.isPlaying())
            if(player.getLives()<=0) {
                if(!window.isInputEnabled())
                    player.setPlaying(false);
                    player.setShowGetName(true);
                    window.enableInput();
            }
            if(!window.isInputEnabled() && !player.isPlaying())
                showMenu();
        items = items.stream().filter(f->f.isToDelete()==false).collect(Collectors.toList());
    }

    private void exit(Window window)
    {
        window.closeWindow();
    }

    private void startGame()
    {
        for(GameItem item : items)
        {
            if(item.menuItem)
            item.visible = false;
        }
    }

    private void showLeaderboards()
    {

    }
    private void showMenu()
    {
        player.setScore(0);
        player.setLives(3);
        for(GameItem item : items)
        {
            if(item.menuItem)
                item.visible = true;
        }
    }

    @Override
    public void render(Window window) {
        window.setClearColor(color, color, color, 0.0f);
        renderer.render(window,items);
        hud.render(window,player);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        items.parallelStream().filter(f->f.getMesh()!=null).forEach(a->a.getMesh().cleanUp());
    }
}
