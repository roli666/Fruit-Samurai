package com.starkindustries.fruitsamurai.Interfaces;

import com.starkindustries.fruitsamurai.Graphics.GameItem;

public interface IHud {
    GameItem[] getGameItems();

    default void cleanup() {
        GameItem[] gameItems = getGameItems();
        for (GameItem gameItem : gameItems) {
            gameItem.getMesh().cleanUp();
        }
    }
}
