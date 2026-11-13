package com.snake.client.features;

import com.snake.client.config.SnakeConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

public class ZoomFeature {
    private static KeyBinding zoomKey;
    private static double prevLog = 0;
    private static double curLog = 0;
    private static double savedSensitivity = -1;

    public static void init() {
        zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.snakeclient.zoom", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Z, "category.snakeclient"));
        ClientTickEvents.END_CLIENT_TICK.register(ZoomFeature::tick);
    }

    private static void tick(MinecraftClient client) {
        SnakeConfig cfg = SnakeConfig.get();
        boolean zooming = cfg.zoomEnabled && client.currentScreen == null && zoomKey.isPressed();

        double target = zooming ? Math.log(cfg.zoomLevel) : 0;
        prevLog = curLog;
        curLog += (target - curLog) * cfg.zoomSmoothness;
        if (Math.abs(target - curLog) < 0.001) curLog = target;

        // Zoom ke time mouse sensitivity kam, taki control rahe
        if (client.options != null) {
            var opt = client.options.getMouseSensitivity();
            if (zooming) {
                if (savedSensitivity < 0) savedSensitivity = opt.getValue();
                opt.setValue(Math.max(0.01, savedSensitivity / Math.exp(curLog)));
            } else if (savedSensitivity >= 0) {
                opt.setValue(savedSensitivity);
                savedSensitivity = -1;
            }
        }
    }

    public static double applyFov(double fov, float tickDelta) {
        double l = MathHelper.lerp(tickDelta, prevLog, curLog);
        if (l < 0.0001) return fov;
        return Math.max(0.1, fov / Math.exp(l));
    }
  }
