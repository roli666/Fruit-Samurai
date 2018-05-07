package com.starkindustries.fruitsamurai.GameLogic;

import com.starkindustries.fruitsamurai.Engine.Window;
import com.starkindustries.fruitsamurai.Graphics.GameItem;
import com.starkindustries.fruitsamurai.Graphics.TextItem;
import com.starkindustries.fruitsamurai.Interfaces.IHud;
import com.starkindustries.fruitsamurai.Utils.FileUtils;

public class HUD implements IHud {

    private final GameItem[] gameItems;

    private final TextItem statusTextItem;

    public HUD(String statusText) throws Exception {
        this.statusTextItem = new TextItem(statusText, FileUtils.getTexturesFolder()+"Characters512x512.bmp",14,14);

        // Create list that holds the items that compose the HUD
        gameItems = new GameItem[]{statusTextItem};
    }

    @Override
    public GameItem[] getGameItems() {
        return gameItems;
    }

    public void updateSize(Window window) {
        this.statusTextItem.setPosition(1f, 1f, -1);
    }
}