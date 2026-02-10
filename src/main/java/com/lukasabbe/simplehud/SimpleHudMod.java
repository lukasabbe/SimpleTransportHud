package com.lukasabbe.simplehud;

import com.lukasabbe.simplehud.config.Config;
import com.lukasabbe.simplehud.huds.ElytraHud;
import com.lukasabbe.simplehud.huds.SimpleHud;
import com.lukasabbe.simplehud.tools.ElytraTools;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;

import java.util.Arrays;
import java.util.List;

public class SimpleHudMod implements ClientModInitializer {

    public static List<SimpleHud> HUD_LIST = Arrays.asList(
            new ElytraHud()
    );

    public static Config configInstance = null;

    @Override
    public void onInitializeClient() {
        Config.HANDLER.load();
        configInstance = Config.HANDLER.instance();
        HUD_LIST.forEach(simpleHud -> HudElementRegistry.addFirst(simpleHud.getIdentifier(), simpleHud::render));
        ClientTickEvents.END_CLIENT_TICK.register(client -> ElytraTools.tickElytraTools());
    }
}
