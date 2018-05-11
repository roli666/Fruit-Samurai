/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starkindustries.fruitsamurai.GameLogic;

import java.util.Random;

/**
 * This class represents the enums used in the project.
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class Enums {
    /**
     * This enum represents a background.
     */
    public enum Background
    {
        DEFAULT,SAMURAI
    }
    /**
     * This enum represents a fruit.
     */
    public enum Fruit
    {
        Melon,Orange,Apple
    }
    /**
     * This enum represents a sword.
     */
    public enum Sword
    {
        //TODO add more sword types
        Glow
    }
    /**
     * This class is used to get a random enumeration.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    static class RandomEnum<E extends Enum<Fruit>> {
        Random RND = new Random();
        E[] values;

        /**
         * Constructor.
         * @param token a generic Enum class parameter
         * @author Aszalós Roland
         * @version 1.0
         * @since Fruit Samurai 0.1
         */
        public RandomEnum(Class<E> token) {
            values = token.getEnumConstants();
        }

        /**
         * Returns a random enum from the enum class specified in the constructor.
         * @author Aszalós Roland
         * @version 1.0
         * @since Fruit Samurai 0.1
         * @return enum
         */
        public E random() {
            return values[RND.nextInt(values.length)];
        }
    }
}
