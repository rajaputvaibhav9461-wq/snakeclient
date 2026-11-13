package com.snake.client.mixin;

import com.snake.client.features.ZoomFeature;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    private void snakeclient$zoom(Camera camera, float tickDelta, boolean changingFov,
                                  CallbackInfoReturnable<Double> cir) {
        if (changingFov) {
            cir.setReturnValue(ZoomFeature.applyFov(cir.getReturnValueD(), tickDelta));
        }
    }
            }
