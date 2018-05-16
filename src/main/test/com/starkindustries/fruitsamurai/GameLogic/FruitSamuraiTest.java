package com.starkindustries.fruitsamurai.GameLogic;

import com.starkindustries.fruitsamurai.Engine.Window;
import com.starkindustries.fruitsamurai.Graphics.GameItem;
import com.starkindustries.fruitsamurai.Utils.RandomUtils;
import org.joml.Vector3f;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FruitSamuraiTest {
    private Window window = null;
    private List<GameItem> items = new ArrayList<>();
    @Test
    public void init() {
    }

    @Test
    public void input() {
    }

    @Before
    public void updateinit() {
        double accelerationY = RandomUtils.floatNumberBetween(-0.8,-0.6);
        double accelerationX = RandomUtils.floatNumberBetween(-0.2,0.2);
        GameItem melon = new GameItem();
        melon.setPosition(0,-16,0);
        melon.setScale(2);
        melon.setAcceleration(new Vector3f((float)accelerationX,(float)accelerationY,0));
        items.add(melon);
    }
    @Test
    public void update(){
        for(GameItem item : items) {
            Vector3f pos = item.getPosition();
            Vector3f acc = item.getAcceleration();
            Assert.assertEquals(pos,item.getPosition());
            Assert.assertEquals(acc,item.getAcceleration());
        }
    }

    @Test
    public void render() {
    }

    @Test
    public void cleanup() {
    }
}