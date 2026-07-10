package net.customcrosshair.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.customcrosshair.CustomCrosshairMod;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigScreenBuilder {
    public static Screen build(Screen parent) {
        CrosshairConfig config = CustomCrosshairMod.CONFIG;
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("title.custom_crosshair.config"))
                .setSavingRunnable(config::save);

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("category.custom_crosshair.general"));
        ConfigCategory highlight = builder.getOrCreateCategory(Text.translatable("category.custom_crosshair.highlight"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        // Категория общих настроек (Линии и цвет)
        general.addEntry(entryBuilder.startAlphaColorField(Text.translatable("option.custom_crosshair.color"), config.color)
                .setDefaultValue(0xFFFFFFFF)
                .setSaveConsumer(newValue -> config.color = newValue)
                .build());

        general.addEntry(entryBuilder.startIntSlider(Text.translatable("option.custom_crosshair.width"), config.width, 1, 20)
                .setDefaultValue(2)
                .setSaveConsumer(newValue -> config.width = newValue)
                .build());

        general.addEntry(entryBuilder.startIntSlider(Text.translatable("option.custom_crosshair.pixel_scale"), config.pixelScale, 1, 10)
                .setDefaultValue(1)
                .setSaveConsumer(newValue -> config.pixelScale = newValue)
                .build());

        general.addEntry(entryBuilder.startIntSlider(Text.translatable("option.custom_crosshair.length"), config.length, 0, 50)
                .setDefaultValue(6)
                .setSaveConsumer(newValue -> config.length = newValue)
                .build());

        general.addEntry(entryBuilder.startIntSlider(Text.translatable("option.custom_crosshair.gap"), config.gap, 0, 50)
                .setDefaultValue(3)
                .setSaveConsumer(newValue -> config.gap = newValue)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.custom_crosshair.dot"), config.dot)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.dot = newValue)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.custom_crosshair.outline"), config.outline)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> config.outline = newValue)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.custom_crosshair.t_shape"), config.tShape)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> config.tShape = newValue)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.custom_crosshair.render_in_third_person"), config.renderInThirdPerson)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> config.renderInThirdPerson = newValue)
                .build());

        // Категория подсветки Entity
        highlight.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.custom_crosshair.highlight_entity"), config.highlightEntity)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.highlightEntity = newValue)
                .build());

        highlight.addEntry(entryBuilder.startAlphaColorField(Text.translatable("option.custom_crosshair.highlight_color"), config.highlightColor)
                .setDefaultValue(0xFFFF0000)
                .setSaveConsumer(newValue -> config.highlightColor = newValue)
                .build());

        return builder.build();
    }
}
