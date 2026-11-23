package dev.moon.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MoonScreen extends Screen {

    public MoonScreen() {
        super(Component.literal("MOON CLIENT BETA"));
    }

    @Override
    protected void init() {
        int center = width / 2;
        int startY = height / 2 - 45;
        int buttonWidth = 180;

        addRenderableWidget(Button.builder(
                getSprintText(),
                button -> {
                    MoonClient.autoSprint = !MoonClient.autoSprint;
                    button.setMessage(getSprintText());
                }
        ).bounds(center - buttonWidth / 2, startY, buttonWidth, 22).build());

        addRenderableWidget(Button.builder(
                getHudText(),
                button -> {
                    MoonClient.hudEnabled = !MoonClient.hudEnabled;
                    button.setMessage(getHudText());
                }
        ).bounds(center - buttonWidth / 2, startY + 30, buttonWidth, 22).build());

        addRenderableWidget(Button.builder(
                Component.literal("Close"),
                button -> onClose()
        ).bounds(center - buttonWidth / 2, startY + 65, buttonWidth, 22).build());
    }

    private Component getSprintText() {
        return Component.literal("Auto Sprint: " +
                (MoonClient.autoSprint ? "ON" : "OFF"));
    }

    private Component getHudText() {
        return Component.literal("HUD: " +
                (MoonClient.hudEnabled ? "ON" : "OFF"));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX,
                       int mouseY, float delta) {

        graphics.fill(0, 0, width, height, 0xC0101018);

        int center = width / 2;

        graphics.fill(
                center - 115, height / 2 - 90,
                center + 115, height / 2 + 100,
                0xFF171B2B
        );

        graphics.drawCenteredString(
                font, "MOON CLIENT BETA",
                center, height / 2 - 75, 0xFF75A9FF
        );

        graphics.drawCenteredString(
                font, "Version 2.1",
                center, height / 2 - 58, 0xFFFFFFFF
        );

        super.render(graphics, mouseX, mouseY, delta);
    }
}
