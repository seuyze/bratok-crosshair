package net.customcrosshair.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class CrosshairConfig {
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("custom_crosshair.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public CrosshairMode mode = CrosshairMode.VANILLA;
    public boolean highlightEntity = true;
    public int highlightColor = 0xFFFF0000; // Красный по умолчанию

    public int color = 0xFFFFFFFF; // Белый по умолчанию
    public int width = 2;
    public int length = 6;
    public int gap = 3;
    public boolean dot = true;
    public boolean outline = false; // Обводка по умолчанию выключена
    public boolean tShape = false;
    public boolean renderInThirdPerson = false; // По умолчанию выключено в F5

    public int pixelScale = 1; // Ювелирный масштаб 1 пиксель GUI по умолчанию

    public boolean[][] pixelGrid = new boolean[31][31];

    public static CrosshairConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                CrosshairConfig config = GSON.fromJson(reader, CrosshairConfig.class);
                if (config != null) {
                    if (config.pixelGrid == null || config.pixelGrid.length != 31 || config.pixelGrid[0].length != 31) {
                        config.pixelGrid = new boolean[31][31];
                    }
                    return config;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        CrosshairConfig defaultConfig = new CrosshairConfig();
        defaultConfig.save();
        return defaultConfig;
    }

    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(this, writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
