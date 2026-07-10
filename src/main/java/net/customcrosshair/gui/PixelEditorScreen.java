package net.customcrosshair.gui;

import net.customcrosshair.CustomCrosshairMod;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class PixelEditorScreen extends Screen {
    private final Screen parent;
    private final int cellSize = 6;
    private final int gridSize = 31;

    public PixelEditorScreen(Screen parent) {
        super(Text.translatable("title.custom_crosshair.pixel_editor"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int gridY = this.height / 2 - (gridSize * cellSize) / 2;
        int bottomY = gridY + gridSize * cellSize + 10;

        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("button.custom_crosshair.clear_grid"),
                button -> {
                    CustomCrosshairMod.CONFIG.pixelGrid = new boolean[gridSize][gridSize];
                    CustomCrosshairMod.CONFIG.save();
                }
        ).dimensions(cx - 110, bottomY, 100, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("gui.done"),
                button -> {
                    CustomCrosshairMod.CONFIG.save();
                    if (this.client != null) {
                        this.client.setScreen(this.parent);
                    }
                }
        ).dimensions(cx + 10, bottomY, 100, 20).build());

        // Кнопки пресетов справа от сетки
        int presetX = cx + 105;
        int presetY = gridY;

        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("button.custom_crosshair.preset_elite"),
                button -> {
                    boolean[][] grid = new boolean[gridSize][gridSize];
                    // Вертикальные линии (крест с отступом в 1 пиксель от центра 15,15)
                    grid[15][11] = grid[15][12] = grid[15][13] = true; // Верх (3 пикселя)
                    grid[15][17] = grid[15][18] = grid[15][19] = true; // Низ (3 пикселя)
                    // Горизонтальные линии (с отступом в 1 пиксель от центра)
                    grid[11][15] = grid[12][15] = grid[13][15] = true; // Лево (3 пикселя)
                    grid[17][15] = grid[18][15] = grid[19][15] = true; // Право (3 пикселя)
                    // Идеальные симметричные уголки (в точности по скриншоту пользователя)
                    // Верх-лево
                    grid[12][12] = grid[13][12] = grid[12][13] = true;
                    // Верх-право
                    grid[18][12] = grid[17][12] = grid[18][13] = true;
                    // Низ-лево
                    grid[12][18] = grid[13][18] = grid[12][17] = true;
                    // Низ-право
                    grid[18][18] = grid[17][18] = grid[18][17] = true;
                    CustomCrosshairMod.CONFIG.pixelGrid = grid;
                    CustomCrosshairMod.CONFIG.save();
                }
        ).dimensions(presetX, presetY, 110, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("button.custom_crosshair.preset_shield"),
                button -> {
                    boolean[][] grid = new boolean[gridSize][gridSize];
                    // Центральный крест
                    grid[12][15] = grid[13][15] = grid[14][15] = true;
                    grid[16][15] = grid[17][15] = grid[18][15] = true;
                    grid[15][12] = grid[15][13] = grid[15][14] = true;
                    grid[15][16] = grid[15][17] = grid[15][18] = true;
                    // Внешние углы (со второго скрина)
                    grid[12][13] = grid[13][12] = true; // Верх-лево
                    grid[18][13] = grid[17][12] = true; // Верх-право
                    grid[12][17] = grid[13][18] = true; // Низ-лево
                    grid[18][17] = grid[17][18] = true; // Низ-право
                    CustomCrosshairMod.CONFIG.pixelGrid = grid;
                    CustomCrosshairMod.CONFIG.save();
                }
        ).dimensions(presetX, presetY + 24, 110, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("button.custom_crosshair.preset_circle"),
                button -> {
                    boolean[][] grid = new boolean[gridSize][gridSize];
                    grid[15][15] = true; // Точка в центре
                    grid[15][12] = grid[14][12] = grid[16][12] = true;
                    grid[15][18] = grid[14][18] = grid[16][18] = true;
                    grid[12][15] = grid[12][14] = grid[12][16] = true;
                    grid[18][15] = grid[18][14] = grid[18][16] = true;
                    grid[13][13] = grid[17][13] = grid[13][17] = grid[17][17] = true;
                    CustomCrosshairMod.CONFIG.pixelGrid = grid;
                    CustomCrosshairMod.CONFIG.save();
                }
        ).dimensions(presetX, presetY + 48, 110, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("button.custom_crosshair.preset_small"),
                button -> {
                    boolean[][] grid = new boolean[gridSize][gridSize];
                    grid[13][15] = grid[14][15] = true;
                    grid[16][15] = grid[17][15] = true;
                    grid[15][13] = grid[15][14] = true;
                    grid[15][16] = grid[15][17] = true;
                    CustomCrosshairMod.CONFIG.pixelGrid = grid;
                    CustomCrosshairMod.CONFIG.save();
                }
        ).dimensions(presetX, presetY + 72, 110, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("button.custom_crosshair.preset_x"),
                button -> {
                    boolean[][] grid = new boolean[gridSize][gridSize];
                    grid[13][13] = grid[14][14] = true;
                    grid[17][13] = grid[16][14] = true;
                    grid[13][17] = grid[14][16] = true;
                    grid[17][17] = grid[16][16] = true;
                    CustomCrosshairMod.CONFIG.pixelGrid = grid;
                    CustomCrosshairMod.CONFIG.save();
                }
        ).dimensions(presetX, presetY + 96, 110, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        int gridX = this.width / 2 - (gridSize * cellSize) / 2;
        int gridY = this.height / 2 - (gridSize * cellSize) / 2;

        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, gridY - 20, 0xFFFFFFFF);

        // Подложка сетки
        context.fill(gridX, gridY, gridX + gridSize * cellSize, gridY + gridSize * cellSize, 0xFF222222);

        int activeColor = CustomCrosshairMod.CONFIG.color;

        // Отрисовка пикселей и линий сетки
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                int px = gridX + x * cellSize;
                int py = gridY + y * cellSize;

                if (CustomCrosshairMod.CONFIG.pixelGrid[x][y]) {
                    context.fill(px, py, px + cellSize, py + cellSize, activeColor);
                }

                // Граница ячейки
                int borderColor = (x == 15 && y == 15) ? 0xFFFFD700 : 0xFF444444; // Золотой центр
                context.fill(px, py, px + cellSize, py + 1, borderColor);
                context.fill(px, py, px + 1, py + cellSize, borderColor);
            }
        }
        // Внешние границы сетки
        context.fill(gridX, gridY + gridSize * cellSize, gridX + gridSize * cellSize + 1, gridY + gridSize * cellSize + 1, 0xFF444444);
        context.fill(gridX + gridSize * cellSize, gridY, gridX + gridSize * cellSize + 1, gridY + gridSize * cellSize + 1, 0xFF444444);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        handleMouseInput(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        handleMouseInput(mouseX, mouseY, button);
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    private void handleMouseInput(double mouseX, double mouseY, int button) {
        int gridX = this.width / 2 - (gridSize * cellSize) / 2;
        int gridY = this.height / 2 - (gridSize * cellSize) / 2;

        if (mouseX >= gridX && mouseX < gridX + gridSize * cellSize && mouseY >= gridY && mouseY < gridY + gridSize * cellSize) {
            int gx = (int) ((mouseX - gridX) / cellSize);
            int gy = (int) ((mouseY - gridY) / cellSize);

            if (gx >= 0 && gx < gridSize && gy >= 0 && gy < gridSize) {
                if (button == 0) { // ЛКМ - рисовать
                    CustomCrosshairMod.CONFIG.pixelGrid[gx][gy] = true;
                } else if (button == 1) { // ПКМ - стирать
                    CustomCrosshairMod.CONFIG.pixelGrid[gx][gy] = false;
                }
            }
        }
    }

    @Override
    public void close() {
        CustomCrosshairMod.CONFIG.save();
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }
}
