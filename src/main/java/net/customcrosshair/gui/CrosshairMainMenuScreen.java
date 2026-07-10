package net.customcrosshair.gui;

import net.customcrosshair.CustomCrosshairMod;
import net.customcrosshair.config.ConfigScreenBuilder;
import net.customcrosshair.config.CrosshairMode;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CrosshairMainMenuScreen extends Screen {
    private final Screen parent;

    public CrosshairMainMenuScreen(Screen parent) {
        super(Text.translatable("title.custom_crosshair.main_menu"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int cy = this.height / 2;
        int btnWidth = 220;
        int btnHeight = 20;

        // Кнопка переключения режима
        this.addDrawableChild(ButtonWidget.builder(
                getModeText(),
                button -> {
                    CrosshairMode next = CrosshairMode.values()[(CustomCrosshairMod.CONFIG.mode.ordinal() + 1) % CrosshairMode.values().length];
                    CustomCrosshairMod.CONFIG.mode = next;
                    CustomCrosshairMod.CONFIG.save();
                    button.setMessage(getModeText());
                }
        ).dimensions(cx - btnWidth / 2, cy - 40, btnWidth, btnHeight).build());

        // Кнопка открытия Cloth Config (параметры линий и подсветки)
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("button.custom_crosshair.open_config"),
                button -> {
                    if (this.client != null) {
                        this.client.setScreen(ConfigScreenBuilder.build(this));
                    }
                }
        ).dimensions(cx - btnWidth / 2, cy - 15, btnWidth, btnHeight).build());

        // Кнопка открытия пиксельного редактора
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("button.custom_crosshair.open_pixel_editor"),
                button -> {
                    if (this.client != null) {
                        this.client.setScreen(new PixelEditorScreen(this));
                    }
                }
        ).dimensions(cx - btnWidth / 2, cy + 10, btnWidth, btnHeight).build());

        // Кнопка Готово / Выход
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("gui.done"),
                button -> {
                    if (this.client != null) {
                        this.client.setScreen(this.parent);
                    }
                }
        ).dimensions(cx - btnWidth / 2, cy + 50, btnWidth, btnHeight).build());
    }

    private Text getModeText() {
        return Text.translatable("mode.custom_crosshair." + CustomCrosshairMod.CONFIG.mode.name().toLowerCase());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2 - 70, 0xFFFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }
}
