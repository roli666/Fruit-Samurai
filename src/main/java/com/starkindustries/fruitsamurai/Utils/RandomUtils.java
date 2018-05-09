package com.starkindustries.fruitsamurai.Utils;

public class RandomUtils{

    public static float floatNumberBetween(double min,double max)
    {
        return (float)(min + (Math.random() * (max - min)));
    }
}
