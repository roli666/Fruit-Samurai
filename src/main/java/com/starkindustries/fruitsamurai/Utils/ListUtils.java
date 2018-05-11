package com.starkindustries.fruitsamurai.Utils;

import java.util.List;

/**
 * This class is a utility class to make my life easier and to keep the code more clean.
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class ListUtils {
    /**
     * Creates a integer array from the supplied integer {@link List}.
     * @param list
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @return ByteBuffer
     */
    public static int[] listIntToArray(List<Integer> list) {
        int[] result = list.stream().mapToInt((Integer v) -> v).toArray();
        return result;
    }
    /**
     * Creates a float array from the supplied float {@link List}.
     * @param list
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @return ByteBuffer
     */
    public static float[] listToFloatArray(List<Float> list) {
        int size = list != null ? list.size() : 0;
        float[] floatArr = new float[size];
        for (int i = 0; i < size; i++) {
            floatArr[i] = list.get(i);
        }
        return floatArr;
    }
}
