
package dev.moon.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MoonScreen extends Screen {

    private String category = "Combat";

    public MoonScreen() {
        super(Component.literal("MOON CLIENT BETA"));
    }

    @Override
    protected void init() {
        int left = width / 2 - 190;
        int top = height / 2 - 115;

        addCategory("Combat", left, top, 45);
        addCategory("Render", left, top, 73);
        addCategory("Movement", left, top, 101);
        addCategory("Misc", left, top, 129);

        int x = left + 115;
        int y = top + 48;

        if (category.equals("Render")) {
            addToggle("FullBright", x, y);
            addToggle("HUD", x, y + 30);
            addToggle("Target HUD", x, y + 60);
            addToggle("Player ESP", x, y + 90);
        }

        if (category.equals("Misc")) {
            addToggle("Pearl Bind", x, y);
            addToggle("Auto Totem", x, y + 30);
        }

        if (category.equals("Movement")) {
            addRenderableWidget(Button.builder(
                    Component.literal("Coming soon"),
                    b -> {}
            ).bounds(x, y, 145, 22).build());
        }

        if (category.equals("Combat")) {
            addRenderableWidget(Button.builder(
                    Component.literal("Combat (later)"),
                    b -> {}
            ).bounds(x, y, 145, 22).build());
        }

        addRenderableWidget(Button.builder(
                Component.literal("Close"),
                b -> onClose()
        ).bounds(left + 135, top + 180, 120, 22).build());
    }

    private void addCategory(String name, int left, int top, int y) {
        addRenderableWidget(Button.builder(
                Component.literal(name),
                b -> {
                    category = name;
                    clearWidgets();
                    init();
                }
        ).bounds(left + 10, top + y, 85, 22).build());
    }

    private void addToggle(String name, int x, int y) {
        boolean enabled = ModuleManager.isEnabled(name);

        addRenderableWidget(Button.builder(
                Component.literal(name + " : " + (enabled ? "ON" : "OFF")),
                button -> {
                    boolean next = ModuleManager.toggle(name);

                    button.setMessage(Component.literal(
                            name + " : " + (next ? "ON" : "OFF")
                    ));
                }
        ).bounds(x, y, 145, 22).build());
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

        graphics.drawString(
                font, category.toUpperCase(),
                left + 115, top + 35, 0xFF8CB9FF
        );

        super.render(graphics, mouseX, mouseY, delta);
    }
}
