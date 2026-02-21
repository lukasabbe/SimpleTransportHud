package com.lukasabbe.simpletransporthud.huds;

import com.lukasabbe.simpletransporthud.Constants;
import com.lukasabbe.simpletransporthud.config.Config;
import com.lukasabbe.simpletransporthud.config.HudPosition;
import com.lukasabbe.simpletransporthud.tools.EntityTools;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.equine.AbstractHorse;

public class HorseHud extends RideableHud {

    @Override
    public void render(GuiGraphics graphics, DeltaTracker tracker) {
        if(!EntityTools.isRidingEntity(AbstractHorse.class)) return;
        super.render(graphics, tracker);
    }

    @Override
    public Identifier getIdentifier() {
        return Constants.HorseHudIdentifier;
    }

    @Override
    public HudPosition getHudPosition() {
        return Config.HANDLER.instance().hudPositionHorse;
    }
}
