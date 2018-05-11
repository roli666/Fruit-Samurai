package com.starkindustries.fruitsamurai.Engine;

/**
 * This class manages time
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class Timer {
    private double lastLoopTime;

    /**
     * Initializes the timer object.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void init() {
        lastLoopTime = getTime();
    }

    /**
     * @return elapsed time since start
     */
    public double getTime() {
        return System.nanoTime() / 1000_000_000.0;
    }

    /**
     * @return elapsed time since start but without calculations
     */
    public float getElapsedTime() {
        double time = getTime();
        float elapsedTime = (float) (time - lastLoopTime);
        lastLoopTime = time;
        return elapsedTime;
    }
    /**
     * @return elapsed time between two frames
     */
    public double getLastLoopTime() {
        return lastLoopTime;
    }
}
