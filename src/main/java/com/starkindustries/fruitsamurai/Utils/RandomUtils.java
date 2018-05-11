package com.starkindustries.fruitsamurai.Utils;
/**
 * This class is a utility class to make my life easier and to keep the code more clean.
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class RandomUtils{
    /**
     * Returns a random float number between min and max.
     * @param min
     * @param max
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @return a float number between min and max
     */
    public static float floatNumberBetween(double min,double max)
    {
        return (float)(min + (Math.random() * (max - min)));
    }
}
