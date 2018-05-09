/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starkindustries.fruitsamurai.GameLogic;

import java.util.Random;

/**
 *
 * @author Raóland
 */
public class Enums {
    public enum Background
    {
        DEFAULT,SAMURAI
    }
    public enum Fruit
    {
        Melon,Orange,Apple
    }
    public enum Sword
    {
        Glow
    }
    static class RandomEnum<E extends Enum<Fruit>> {
        Random RND = new Random();
        E[] values;

        public RandomEnum(Class<E> token) {
            values = token.getEnumConstants();
        }

        public E random() {
            return values[RND.nextInt(values.length)];
        }
    }
}
