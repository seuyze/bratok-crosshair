package net.customcrosshair.mixin;

import net.customcrosshair.CustomCrosshairMod;
import net.customcrosshair.config.CrosshairMode;
import net.customcrosshair.render.CrosshairRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "renderCrosshair(Lnet/minecraft/client/gui/DrawContext;)V", at = @At("HEAD"), cancellable = true)
    private void onRenderCrosshairHead(DrawContext context, CallbackInfo ci) {
        if (CustomCrosshairMod.CONFIG != null) {
            net.minecraft.client.MinecraftClient client = net.minecraft.client.MinecraftClient.getInstance();
            if (!CustomCrosshairMod.CONFIG.renderInThirdPerson && client != null && !client.options.getPerspective().isFirstPerson()) {
                ci.cancel();
                return;
            }

            if (CustomCrosshairMod.CONFIG.mode != CrosshairMode.VANILLA) {
                CrosshairRenderer.render(context, CustomCrosshairMod.CONFIG);
                ci.cancel();
            }
        }
    }
}
