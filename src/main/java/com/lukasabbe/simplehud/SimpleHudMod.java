package com.lukasabbe.simplehud;

import com.lukasabbe.simplehud.config.Config;
import com.lukasabbe.simplehud.huds.BoatHud;
import com.lukasabbe.simplehud.huds.ElytraHud;
import com.lukasabbe.simplehud.huds.MinecartHud;
import com.lukasabbe.simplehud.huds.SimpleHud;
import com.lukasabbe.simplehud.tools.ElytraTools;
import com.lukasabbe.simplehud.tools.EntityTools;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

import java.util.Arrays;
import java.util.List;

public class SimpleHudMod implements ClientModInitializer {

    public static List<SimpleHud> HUD_LIST = Arrays.asList(
            new ElytraHud(),
            new BoatHud(),
            new MinecartHud()
    );

    @Override
    public void onInitializeClient() {
        Config.HANDLER.load();
        Config.HANDLER.load();
        HUD_LIST.forEach(simpleHud -> HudElementRegistry.addFirst(
                simpleHud.getIdentifier(),
                (guiGraphics, deltaTracker) -> renderScaled(guiGraphics, deltaTracker, simpleHud))
        );
        ClientTickEvents.END_CLIENT_TICK.register(client -> ElytraTools.tickElytraTools());
        ClientTickEvents.END_CLIENT_TICK.register(client -> EntityTools.tickEntityTools());
    }

    public static void renderScaled(GuiGraphics graphics, DeltaTracker tracker, SimpleHud simpleHud){
        var stack = graphics.pose();
        final float scale = (float) Config.HANDLER.instance().hudScale / 10;
        int width = graphics.guiWidth();
        int height = graphics.guiHeight();
        stack.pushMatrix();
        stack.translate(width / 2f, height / 2f);
        stack.scale(scale, scale);
        stack.translate(-width / 2f, -height / 2f);
        simpleHud.render(graphics, tracker);
        stack.popMatrix();
    }
}
