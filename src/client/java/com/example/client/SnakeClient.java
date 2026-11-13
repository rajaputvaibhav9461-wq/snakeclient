package com.snake.client;

import com.snake.client.config.SnakeConfig;
import com.snake.client.features.SettingsFeature;
import com.snake.client.features.ZoomFeature;
import net.fabricmc.api.ClientModInitializer;

public class SnakeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SnakeConfig.load();
        SettingsFeature.init();
        ZoomFeature.init();
    }
}
