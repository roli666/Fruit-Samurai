package com.starkindustries.fruitsamurai.Meshes;

import com.starkindustries.fruitsamurai.Graphics.Texture;
import com.starkindustries.fruitsamurai.Graphics.VertexArray;
import com.starkindustries.fruitsamurai.Utils.FileUtils;
import org.joml.Vector3f;

public class Sword {

    private boolean slashing;
    private float SIZE;
    private VertexArray mesh;
    private Texture texture;

    private Vector3f position = new Vector3f();
    //private float delta = 0.0f;

    public Sword() {
        SIZE = 10.0f;
        float[] vertices = new float[] {
                -SIZE / 2.0f, -SIZE / 2.0f, 0.2f,
                -SIZE / 2.0f,  SIZE / 2.0f, 0.2f,
                SIZE / 2.0f,  SIZE / 2.0f, 0.2f,
                SIZE / 2.0f, -SIZE / 2.0f, 0.2f
        };

        byte[] indices = new byte[] {
                0, 1, 2,
                2, 3, 0
        };

        float[] tcs = new float[] {
                0, 1,
                0, 0,
                1, 0,
                1, 1
        };

        mesh = new VertexArray(vertices, indices, tcs);
        texture = new Texture(FileUtils.getTexturesFolder() + "\\sword.png");
        slashing = false;
    }
}
