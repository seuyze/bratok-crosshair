package net.customcrosshair;

import net.customcrosshair.config.CrosshairConfig;
import net.customcrosshair.gui.CrosshairMainMenuScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class CustomCrosshairMod implements ClientModInitializer {
    public static CrosshairConfig CONFIG;
    private static KeyBinding configKey;

    @Override
    public void onInitializeClient() {
        CONFIG = CrosshairConfig.load();

        configKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.custom_crosshair.config",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_GRAVE_ACCENT, // Клавиша ~ (Ё)
                "category.custom_crosshair"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (configKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new CrosshairMainMenuScreen(client.currentScreen));
                }
            }
        });
    }
}
