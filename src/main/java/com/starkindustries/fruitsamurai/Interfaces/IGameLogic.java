package com.starkindustries.fruitsamurai.Interfaces;

import com.starkindustries.fruitsamurai.Engine.Window;
import com.starkindustries.fruitsamurai.Graphics.GameItem;

public interface IGameLogic {

    void init(Window window) throws Exception;

    void input(Window window);

    void update(float interval);

    void render(Window window,GameItem[] items);
    
    void cleanup();

}
