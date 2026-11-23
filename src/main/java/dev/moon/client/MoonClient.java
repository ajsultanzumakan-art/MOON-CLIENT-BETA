
package dev.moon.client;

import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public class MoonClient implements ClientModInitializer {

    private static KeyMapping openMenu;
    private static Double originalGamma = null;

    @Override
    public void onInitializeClient() {

        KeyMapping.Category category = KeyMapping.Category.register(
                Identifier.fromNamespaceAndPath("moonclient", "main")
        );

        openMenu = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.moonclient.open_menu",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            while (openMenu.consumeClick()) {
                if (client.screen == null) {
                    client.setScreen(new MoonScreen());
                } else if (client.screen instanceof MoonScreen) {
                    client.setScreen(null);
                }
            }

            if (client.options == null) return;

            boolean enabled = false;

            if (enabled) {
                if (originalGamma == null) {
                    originalGamma = client.options.gamma().get();
                }
                client.options.gamma().set(16.0);
            } else if (originalGamma != null) {
                client.options.gamma().set(originalGamma);
                originalGamma = null;
            }
        });

        System.out.println("[MOON CLIENT BETA] Loaded!");
    }
}
