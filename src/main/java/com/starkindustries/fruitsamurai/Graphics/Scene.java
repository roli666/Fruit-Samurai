package com.starkindustries.fruitsamurai.Graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scene {
    private Map<Mesh, List<GameItem>> meshMap;

    public Scene() {
        meshMap = new HashMap<>();
    }

    public Map<Mesh, List<GameItem>> getGameMeshes() {
        return meshMap;
    }

    public void setGameItems(GameItem[] gameItems) {
        // Create a map of meshes to speed up rendering
        int numGameItems = gameItems != null ? gameItems.length : 0;
        for (int i = 0; i < numGameItems; i++) {
            GameItem gameItem = gameItems[i];
            Mesh[] meshes = gameItem.getMeshes();
            for (Mesh mesh : meshes) {
                List<GameItem> list = meshMap.get(mesh);
                if (list == null) {
                    list = new ArrayList<>();
                    meshMap.put(mesh, list);
                }
                list.add(gameItem);
            }
        }
    }
    public void setGameItems(List<GameItem> gameItems) {
        // Create a map of meshes to speed up rendering
        int numGameItems = gameItems.size();
        for (int i = 0; i < numGameItems; i++) {
            GameItem gameItem = gameItems.get(i);
            Mesh[] meshes = gameItem.getMeshes();
            for (Mesh mesh : meshes) {
                List<GameItem> list = meshMap.get(mesh);
                if (list == null) {
                    list = new ArrayList<>();
                    meshMap.put(mesh, list);
                }
                list.add(gameItem);
            }
        }
    }

    public void cleanup() {
        for (Mesh mesh : meshMap.keySet()) {
            mesh.cleanUp();
        }
    }
}
