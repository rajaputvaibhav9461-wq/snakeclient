package com.snake.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SnakeConfig {
    // Zoom
    public boolean zoomEnabled = true;
    public double zoomLevel = 10;        // 2 se 100
    public double zoomSmoothness = 0.25; // 0.05 (slow) se 1.0 (instant)

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("snakeclient.json");
    private static SnakeConfig instance = new SnakeConfig();

    public static SnakeConfig get() {
        return instance;
    }

    public static void load() {
        try {
            if (Files.exists(PATH)) {
                SnakeConfig loaded = GSON.fromJson(Files.readString(PATH), SnakeConfig.class);
                if (loaded != null) instance = loaded;
            } else {
                save();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            Files.writeString(PATH, GSON.toJson(instance));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
