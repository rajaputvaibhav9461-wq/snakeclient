package com.snake.client.gui;

import com.snake.client.config.SnakeConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class SettingsScreen extends Screen {
    private final Screen parent;

    public SettingsScreen(Screen parent) {
        super(Text.literal("SnakeClient Settings"));
        this.parent = parent;
    }

    private static Text zoomLabel(boolean on) {
        return Text.literal("SnakeZoom: " + (on ? "ON" : "OFF"));
    }

    @Override
    protected void init() {
        SnakeConfig cfg = SnakeConfig.get();
        int w = 200;
        int x = this.width / 2 - w / 2;
        int y = this.height / 4;

        // Zoom on/off
        addDrawableChild(ButtonWidget.builder(zoomLabel(cfg.zoomEnabled), b -> {
            cfg.zoomEnabled = !cfg.zoomEnabled;
            b.setMessage(zoomLabel(cfg.zoomEnabled));
        }).dimensions(x, y, w, 20).build());

        // Zoom level slider (2x - 100x)
        addDrawableChild(new SliderWidget(x, y + 26, w, 20, Text.empty(), (cfg.zoomLevel - 2) / 98.0) {
            { updateMessage(); }
            @Override
            protected void updateMessage() {
                setMessage(Text.literal("Zoom Level: " + Math.round(2 + this.value * 98) + "x"));
            }
            @Override
            protected void applyValue() {
                cfg.zoomLevel = Math.round(2 + this.value * 98);
            }
        });

        // Smoothness slider (0.05 - 1.0)
        addDrawableChild(new SliderWidget(x, y + 52, w, 20, Text.empty(), (cfg.zoomSmoothness - 0.05) / 0.95) {
            { updateMessage(); }
            @Override
            protected void updateMessage() {
                int percent = (int) Math.round(this.value * 100);
                setMessage(Text.literal("Zoom Speed: " + percent + "%"));
            }
            @Override
            protected void applyValue() {
                cfg.zoomSmoothness = 0.05 + this.value * 0.95;
            }
        });

        // Done button
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, b -> close())
                .dimensions(x, y + 90, w, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }

    @Override
    public void removed() {
        SnakeConfig.save();
    }
          }
