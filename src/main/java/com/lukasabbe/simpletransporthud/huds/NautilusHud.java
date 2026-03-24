package com.lukasabbe.simpletransporthud.huds;

import com.lukasabbe.simpletransporthud.Constants;
import com.lukasabbe.simpletransporthud.config.Config;
import com.lukasabbe.simpletransporthud.config.HudPosition;
import com.lukasabbe.simpletransporthud.config.SpeedEnum;
import com.lukasabbe.simpletransporthud.tools.EntityTools;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.nautilus.AbstractNautilus;

public class NautilusHud extends RideableHud {

    @Override
    public void render(GuiGraphicsExtractor graphics, DeltaTracker tracker) {
        if(!EntityTools.isRidingEntity(AbstractNautilus.class)) return;
        super.render(graphics, tracker);
    }

    @Override
    public SpeedEnum getSpeedEnum() {
        return Config.HANDLER.instance().speedEnumNautilus;
    }

    @Override
    public int getDelay() {
        return Config.HANDLER.instance().nautilusHudDelay;
    }

    @Override
    public Identifier getIdentifier() {
        return Constants.NautilusHudIdentifier;
    }

    @Override
    public HudPosition getHudPosition() {
        return Config.HANDLER.instance().hudPositionNautilus;
    }
}
