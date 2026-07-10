package net.customcrosshair.render;

import net.customcrosshair.config.CrosshairConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.hit.HitResult;

public class CrosshairRenderer {
    public static boolean isLookingAtEntity() {
        MinecraftClient client = MinecraftClient.getInstance();
        return client != null && client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.ENTITY;
    }

    public static void render(DrawContext context, CrosshairConfig config) {
        int cx = context.getScaledWindowWidth() / 2;
        int cy = context.getScaledWindowHeight() / 2;

        int color = (config.highlightEntity && isLookingAtEntity()) ? config.highlightColor : config.color;

        if (config.mode == net.customcrosshair.config.CrosshairMode.LINES) {
            renderLines(context, config, cx, cy, color);
        } else if (config.mode == net.customcrosshair.config.CrosshairMode.PIXEL) {
            renderPixelGrid(context, config, cx, cy, color);
        }
    }

    private static void renderLines(DrawContext context, CrosshairConfig config, int cx, int cy, int color) {
        int w = config.width;
        int l = config.length;
        int g = config.gap;
        boolean outline = config.outline;

        int dotX = cx - w / 2;
        int dotY = cy - w / 2;

        if (config.dot) {
            drawRectWithOutline(context, dotX, dotY, w, w, color, outline);
        }

        if (l > 0) {
            drawRectWithOutline(context, dotX - g - l, dotY, l, w, color, outline);
            drawRectWithOutline(context, dotX + w + g, dotY, l, w, color, outline);
            if (!config.tShape) {
                drawRectWithOutline(context, dotX, dotY - g - l, w, l, color, outline);
            }
            drawRectWithOutline(context, dotX, dotY + w + g, w, l, color, outline);
        }
    }

    private static void renderPixelGrid(DrawContext context, CrosshairConfig config, int cx, int cy, int color) {
        int scale = config.pixelScale; // Масштаб пикселя (по умолчанию 1)
        int startX = cx - 15 * scale - scale / 2;
        int startY = cy - 15 * scale - scale / 2;
        boolean outline = config.outline;

        // Сначала рисуем всю обводку
        if (outline) {
            for (int x = 0; x < 31; x++) {
                for (int y = 0; y < 31; y++) {
                    if (config.pixelGrid[x][y]) {
                        int px = startX + x * scale;
                        int py = startY + y * scale;
                        context.fill(px - 1, py - 1, px + scale + 1, py + scale + 1, 0xFF000000);
                    }
                }
            }
        }

        // Затем поверх рисуем активные пиксели цветом
        for (int x = 0; x < 31; x++) {
            for (int y = 0; y < 31; y++) {
                if (config.pixelGrid[x][y]) {
                    int px = startX + x * scale;
                    int py = startY + y * scale;
                    context.fill(px, py, px + scale, py + scale, color);
                }
            }
        }
    }

    private static void drawRectWithOutline(DrawContext context, int x, int y, int w, int h, int color, boolean outline) {
        if (outline) {
            context.fill(x - 1, y - 1, x + w + 1, y + h + 1, 0xFF000000);
        }
        context.fill(x, y, x + w, y + h, color);
    }
}
