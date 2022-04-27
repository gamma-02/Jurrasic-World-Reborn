package net.gamma02.jurassicworldreborn.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.gamma02.jurassicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class JsonOutputGenerator {
    public static void doJsonProcessing(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        HashMap<JsonObject, String> jsons = DynamicWoodTypeRegistry.getJsonBlockStateModelDefinitions();
        String outputPath = FileUtils.getUserDirectoryPath() + "/Downloads/json_output/";
        for(JsonObject object : jsons.keySet()) {
            try (PrintWriter writer = new PrintWriter(outputPath + jsons.get(object) + ".json")) {
                String json = gson.toJson(object);
                writer.println(json);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
