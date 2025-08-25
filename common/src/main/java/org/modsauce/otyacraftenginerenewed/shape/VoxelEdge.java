package org.modsauce.otyacraftenginerenewed.shape;

import com.google.gson.JsonArray;

public record VoxelEdge(double stX, double stY, double stZ, double enX, double enY, double enZ) {
    public static VoxelEdge parse(JsonArray ja) {
        if (ja.size() != 6) return null;
        for (int i = 0; i < 6; i++) {
            if (!ja.get(i).isJsonPrimitive())
                return null;
        }
        return new VoxelEdge(ja.get(0).getAsDouble(), ja.get(1).getAsDouble(), ja.get(2).getAsDouble(), ja.get(3).getAsDouble(), ja.get(4).getAsDouble(), ja.get(5).getAsDouble());
    }
}
