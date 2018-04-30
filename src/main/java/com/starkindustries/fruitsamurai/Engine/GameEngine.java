package com.starkindustries.fruitsamurai.Engine;

import com.bulletphysics.dynamics.DynamicsWorld;
import com.starkindustries.fruitsamurai.Interfaces.IGameLogic;

public class GameEngine implements Runnable {

    private final Thread game;
    private final Window window;
    private final IGameLogic gameLogic;
    public static final int TARGET_FPS = 75;
    public static final int TARGET_UPS = 30;
    private final Timer timer;

    public GameEngine(String windowTitle,int width,int height,boolean vSync,IGameLogic gameLogic) throws Exception
    {
        game = new Thread(this, "GAME_LOOP_THREAD");
        window = new Window(windowTitle, width, height, vSync);
        this.gameLogic = gameLogic;
        timer = new Timer();
    }

    @Override
    public void run()
    {
        try {
            init();
            gameLoop();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        	cleanup();
        }
    }

    private void cleanup() {
    	gameLogic.cleanup();
	}

	public void start()
    {
        String osName = System.getProperty("os.name");
        if ( osName.contains("Mac") ) {
            game.run();
        } else {
            game.start();
        }
    }

    protected void init() throws Exception {
        window.init();
        timer.init();
        gameLogic.init(window);
    }

    protected void input() {
        gameLogic.input(window);
    }

    protected void update(float interval) {
        gameLogic.update(interval);
    }

    protected void render() {
        gameLogic.render(window);
        window.update();
    }

    protected void gameLoop()
    {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        boolean running = true;
        while (running && !window.windowShouldClose()) {
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            input();

            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }

            render();

            if (!window.isvSync()) {
                sync();
            }
        }
    }

    private void sync()
    {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
            }
        }
    }


}
