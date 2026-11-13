package com.snake.client.features;

import com.snake.client.gui.SettingsScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SettingsFeature {
    private static KeyBinding settingsKey;

    public static void init() {
        settingsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.snakeclient.settings", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "category.snakeclient"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (settingsKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new SettingsScreen(null));
                }
            }
        });
    }
}
