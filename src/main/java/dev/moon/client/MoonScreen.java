
package dev.moon.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MoonScreen extends Screen {

    private String category = "Combat";

    private boolean killAura = false;
    private boolean fullBright = false;
    private boolean hud = true;
    private boolean targetHud = true;
    private boolean playerEsp = false;
    private boolean pearlBind = false;
    private boolean autoTotem = false;

    public MoonScreen() {
        super(Component.literal("MOON CLIENT BETA"));
    }

    @Override
    protected void init() {
        int left = width / 2 - 190;
        int top = height / 2 - 115;

        addRenderableWidget(Button.builder(
                Component.literal("Combat"),
                b -> changeCategory("Combat")
        ).bounds(left + 10, top + 45, 85, 22).build());

        addRenderableWidget(Button.builder(
                Component.literal("Render"),
                b -> changeCategory("Render")
        ).bounds(left + 10, top + 73, 85, 22).build());

        addRenderableWidget(Button.builder(
                Component.literal("Movement"),
                b -> changeCategory("Movement")
        ).bounds(left + 10, top + 101, 85, 22).build());

        addRenderableWidget(Button.builder(
                Component.literal("Misc"),
                b -> changeCategory("Misc")
        ).bounds(left + 10, top + 129, 85, 22).build());

        addModuleButtons(left, top);
        
        addRenderableWidget(Button.builder(
                Component.literal("Close"),
                b -> onClose()
        ).bounds(left + 135, top + 180, 120, 22).build());
    }

    private void changeCategory(String name) {
        category = name;
        clearWidgets();
        init();
    }

    private void addModuleButtons(int left, int top) {
        int x = left + 115;
        int y = top + 48;

        if (category.equals("Combat")) {
            addToggle("KillAura", killAura, y,
                    value -> killAura = value);
        }

        if (category.equals("Render")) {
            addToggle("FullBright", fullBright, y,
                    value -> fullBright = value);
            addToggle("HUD", hud, y + 30,
                    value -> hud = value);
            addToggle("Target HUD", targetHud, y + 60,
                    value -> targetHud = value);
            addToggle("Player ESP", playerEsp, y + 90,
                    value -> playerEsp = value);
        }

        if (category.equals("Movement")) {
            addRenderableWidget(Button.builder(
                    Component.literal("No modules yet"),
                    b -> {}
            ).bounds(x, y, 145, 22).build());
        }

        if (category.equals("Misc")) {
            addToggle("Pearl Bind", pearlBind, y,
                    value -> pearlBind = value);
            addToggle("Auto Totem", autoTotem, y + 30,
                    value -> autoTotem = value);
        }
    }

    private void addToggle(
            String name,
            boolean enabled,
            int y,
            java.util.function.Consumer<Boolean> setter
    ) {
        int left = width / 2 - 190;

        addRenderableWidget(Button.builder(
                Component.literal(name + " : " +
                        (enabled ? "ON" : "OFF")),
                button -> {
                    boolean next = !enabled;
                    setter.accept(next);
                    button.setMessage(Component.literal(
                            name + " : " + (next ? "ON" : "OFF")
                    ));
                }
        ).bounds(left + 115, y, 145, 22).build());
    }

    @Override
    public void render(
            GuiGraphics graphics,
            int mouseX,
            int mouseY,
            float delta
    ) {
        graphics.fill(0, 0, width, height, 0xB0000000);

        int left = width / 2 - 190;
        int top = height / 2 - 115;

        graphics.fill(
                left, top, left + 380, top + 220,
                0xFF101522
        );

        graphics.fill(
                left, top, left + 380, top + 32,
                0xFF202D48
        );

        graphics.drawString(
                font, "MOON CLIENT BETA",
                left + 12, top + 12, 0xFF8CB9FF
        );

        graphics.drawString(
                font, "2.0.0 BETA",
                left + 295, top + 12, 0xFFB8C9E8
        );

        graphics.fill(
                left + 105, top + 38,
                left + 106, top + 170,
                0xFF34496D
        );

        graphics.drawString(
                font, category.toUpperCase(),
                left + 115, top + 35, 0xFF8CB9FF
        );

        super.render(graphics, mouseX, mouseY, delta);
    }
}
