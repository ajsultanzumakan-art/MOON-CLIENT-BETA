package dev.moon.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MoonScreen extends Screen {

    public MoonScreen() {
        super(Component.literal("MOON CLIENT BETA"));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        graphics.fill(0, 0, width, height, 0xC0101018);

        int centerX = width / 2;
        int centerY = height / 2;

        graphics.fill(
                centerX - 110,
                centerY - 80,
                centerX + 110,
                centerY + 80,
                0xFF171B2B
        );

        graphics.drawCenteredString(
                font,
                "MOON CLIENT BETA",
                centerX,
                centerY - 60,
                0xFF75A9FF
        );

        graphics.drawCenteredString(
                font,
                "Version 2.0 Beta",
                centerX,
                centerY - 40,
                0xFFFFFFFF
        );

        super.render(graphics, mouseX, mouseY, delta);
    }
}
