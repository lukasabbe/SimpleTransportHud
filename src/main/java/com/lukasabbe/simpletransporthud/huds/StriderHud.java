package com.lukasabbe.simpletransporthud.huds;

import com.lukasabbe.simpletransporthud.Constants;
import com.lukasabbe.simpletransporthud.config.Config;
import com.lukasabbe.simpletransporthud.config.HudPosition;
import com.lukasabbe.simpletransporthud.config.SpeedEnum;
import com.lukasabbe.simpletransporthud.tools.EntityTools;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.Strider;

public class StriderHud extends RideableHud{
    @Override
    public void render(GuiGraphicsExtractor graphics, DeltaTracker tracker) {
        if(!EntityTools.isRidingEntity(Strider.class)) return;
        super.render(graphics, tracker);
    }

    @Override
    public Identifier getIdentifier() {
        return Constants.StriderHudIdentifier;
    }

    @Override
    public HudPosition getHudPosition() {
        return Config.HANDLER.instance().hudPositionStrider;
    }

    @Override
    public SpeedEnum getSpeedEnum() {
        return Config.HANDLER.instance().speedEnumStrider;
    }

    @Override
    public int getDelay() {
        return Config.HANDLER.instance().striderHudDelay;
    }
}
