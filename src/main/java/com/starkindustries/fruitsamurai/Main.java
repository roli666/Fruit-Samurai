package com.starkindustries.fruitsamurai;

import com.starkindustries.fruitsamurai.Engine.GameEngine;
import com.starkindustries.fruitsamurai.Engine.Window;
import com.starkindustries.fruitsamurai.GameLogic.FruitSamurai;
import com.starkindustries.fruitsamurai.Interfaces.IGameLogic;

/**
 * The Main class of the application
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class Main {
    /**
     * Standard Main method to start the application.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @param args
     */
    public static void main(String[] args) {
        try {
            boolean vsync = true;
            IGameLogic gameLogic = new FruitSamurai();
            Window.WindowOptions opts = new Window.WindowOptions();
            opts.showFps = true;
            opts.compatibleProfile = true;
            opts.antialiasing = true;
            GameEngine gameEng = new GameEngine("Fruit Samurai", 800, 600, vsync, opts, gameLogic);
            gameEng.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
