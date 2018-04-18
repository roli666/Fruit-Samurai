package com.starkindustries.fruitsamurai;

import com.starkindustries.fruitsamurai.Engine.GameEngine;
import com.starkindustries.fruitsamurai.GameLogic.FruitSamurai;
import com.starkindustries.fruitsamurai.Interfaces.IGameLogic;

public class Main {
    public static void main(String[] args) {
        try {
            boolean vsync = true;
            IGameLogic gameLogic = new FruitSamurai();
            GameEngine gameEng = new GameEngine("Fruit Samurai", 800, 600, vsync, gameLogic);
            gameEng.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
