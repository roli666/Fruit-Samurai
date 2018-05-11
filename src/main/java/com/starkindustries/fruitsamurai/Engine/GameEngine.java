package com.starkindustries.fruitsamurai.Engine;
import com.starkindustries.fruitsamurai.Interfaces.IGameLogic;

/**
 * This class manages everything, this is the engine of the game.
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class GameEngine implements Runnable {

    private final Thread game;
    private final Window window;
    private final IGameLogic gameLogic;
    public static final int TARGET_FPS = 75;
    public static final int TARGET_UPS = 30;
    private final Timer timer;
    private double lastFps;
    private int fps;
    /**
     * Standard constructor initializes:
     * <ul>
     *     <li>The game thread</li>
     *     <li>The window</li>
     *     <li>The game logic</li>
     *     <li>The timer</li>
     * </ul>
     * @param windowTitle The title of the window
     * @param width The width of the window
     * @param height The height of the window
     * @param vSync Toggles VSync
     * @param opts A {@link Window.WindowOptions} object to set additional window options
     * @param gameLogic A gameLogic interface
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @throws Exception
     */
    public GameEngine(String windowTitle,int width,int height,boolean vSync,Window.WindowOptions opts,IGameLogic gameLogic) throws Exception
    {
        game = new Thread(this, "GAME_LOOP_THREAD");
        window = new Window(windowTitle, width, height, vSync, opts);
        this.gameLogic = gameLogic;
        timer = new Timer();
    }
    /**
     * This method runs the {@link #init()} and the {@link #gameLoop()} methods.
     * At program termination runs the {@link #cleanup()} method as well.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
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
    /**
     * This method the {@link IGameLogic} interface cleanup method.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    private void cleanup() {
    	gameLogic.cleanup();
	}
    /**
     * Starts the game Thread according to the current operating system.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
	public void start()
    {
        String osName = System.getProperty("os.name");
        if ( osName.contains("Mac") ) {
            game.run();
        } else {
            game.start();
        }
    }
    /**
     * This method initializes the window,timer, game logic and the fps counter.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @throws Exception
     */
    protected void init() throws Exception {
        window.init();
        timer.init();
        gameLogic.init(window);
        lastFps = timer.getTime();
        fps = 0;
    }
    /**
     * Handles player input.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    protected void input() {
        gameLogic.input(window);
    }
    /**
     * Handles updates before rendering.
     * @param interval time between two framse
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    protected void update(float interval) {
        gameLogic.update(interval,window);
    }
    /**
     * Handles rendering.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    protected void render() {
        if ( window.getOptions().showFps && timer.getLastLoopTime() - lastFps > 1 ) {
            lastFps = timer.getLastLoopTime();
            window.setWindowTitle(window.getWindowTitle() + " - " + fps + " FPS");
            fps = 0;
        }
        fps++;
        gameLogic.render(window);
        window.update();
    }
    /**
     * The standard game loop.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
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
