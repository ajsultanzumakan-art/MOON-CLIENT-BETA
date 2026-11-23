
package dev.moon.client;

import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

import java.nio.file.*;
import java.util.*;

public class MoonClient implements ClientModInitializer {

    private static KeyMapping openMenu;
    private static Double originalGamma = null;

    @Override
    public void onInitializeClient() {

        KeyMapping.Category category = KeyMapping.Category.register(
                Identifier.fromNamespaceAndPath("moonclient", "main")
        );

        openMenu = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.moonclient.open_menu",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_RIGHT_SHIFT,
                        category
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            while (openMenu.consumeClick()) {
                if (client.screen == null) {
                    client.setScreen(new MoonScreen());
                } else if (client.screen instanceof MoonScreen) {
                    client.setScreen(null);
                }
            }

            if (client.options == null) return;

            boolean enabled = ModuleManager.isEnabled("FullBright");

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

final class ModuleManager {

    private static final Map<String, Boolean> MODULES =
            new LinkedHashMap<>();

    private static final Path FILE =
            FabricLoader.getInstance().getConfigDir()
                    .resolve("moonclient.properties");

    static {
        MODULES.put("FullBright", false);
        MODULES.put("HUD", true);
        MODULES.put("Target HUD", true);
        MODULES.put("Player ESP", false);
        MODULES.put("Pearl Bind", false);
        MODULES.put("Auto Totem", false);
        load();
    }

    private ModuleManager() {}

    public static boolean isEnabled(String name) {
        return MODULES.getOrDefault(name, false);
    }

    public static boolean toggle(String name) {
        boolean value = !isEnabled(name);

        if (MODULES.containsKey(name)) {
            MODULES.put(name, value);
            save();
        }

        return value;
    }

    private static void load() {
        if (!Files.exists(FILE)) return;

        Properties p = new Properties();

        try (var in = Files.newInputStream(FILE)) {
            p.load(in);
            MODULES.replaceAll((name, old) ->
                    Boolean.parseBoolean(
                            p.getProperty(name, old.toString())
                    )
            );
        } catch (Exception e) {
            System.err.println("[MOON] Load error: " + e);
        }
    }

    private static void save() {
        Properties p = new Properties();

        MODULES.forEach((k, v) ->
                p.setProperty(k, v.toString())
        );

        try {
            Files.createDirectories(FILE.getParent());
            try (var out = Files.newOutputStream(FILE)) {
                p.store(out, "MOON CLIENT");
            }
        } catch (Exception e) {
            System.err.println("[MOON] Save error: " + e);
        }
    }
}
