package com.starkindustries.fruitsamurai.Utils;

import com.starkindustries.fruitsamurai.Graphics.Mesh;
import org.junit.Test;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

public class OBJLoaderTest {

    @Test
    public void loadmesh() {
        try {
            Mesh mesh = OBJLoader.loadmesh("/meshes/melon.obj");
            assertNotNull(mesh);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}