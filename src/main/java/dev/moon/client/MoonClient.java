package dev.moon.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class MoonClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("[MOON CLIENT BETA] Loaded successfully!");
    }
}
