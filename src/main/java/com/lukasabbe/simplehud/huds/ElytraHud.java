package com.lukasabbe.simplehud.huds;

import com.lukasabbe.simplehud.Constants;
import com.lukasabbe.simplehud.SimpleHudMod;
import com.lukasabbe.simplehud.tools.ElytraTools;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;

public class ElytraHud implements SimpleHud {
    @Override
    public void render(GuiGraphics graphics, DeltaTracker tracker) {
        if(!isHudActivated()) return;
        //if(!ElytraTools.isFlying()) return;
        if(client.noRender) return;
        if(client.player == null) return;

        int[] pos = getCornerPos();
        int x = pos[0];
        int y = pos[1];

        renderBackPlate(graphics);

        int textX = 5;
        float textScale = 0.6f;
        int whiteColor = 0xFFFFFFFF;

        int pitchTextY = 5;
        renderCenteredScaledText(graphics, String.format("%d°", ElytraTools.getPitch()), x + textX, y + pitchTextY, whiteColor, textScale);

        int speedTextY = 15;
        renderCenteredScaledText(graphics, getSpeed(), x + textX, y + speedTextY, whiteColor, textScale);

        int coordinatesTextY = 25;
        String coordinatesText = String.format("%.0f:%.0f:%.0f", client.player.getX(), client.player.getY(), client.player.getZ());

        int maxAvailableWidth = 40;
        int textWidth = client.font.width(coordinatesText);

        float maxScale = (float) maxAvailableWidth / textWidth;
        float finalScale = Math.min(textScale, maxScale);

        renderCenteredScaledText(graphics, coordinatesText, x + textX, y + coordinatesTextY, whiteColor, finalScale);
    }

    private String getSpeed(){
        return switch (SimpleHudMod.configInstance.speedEnum){
            case kmh -> String.format("%.1f km/h", ElytraTools.getSpeedKmh());
            case mph -> String.format("%.1f mph", ElytraTools.getSpeedMph());
            case ms -> String.format("%.1f m/s", ElytraTools.getSpeedMs());
        };
    }

    @Override
    public Identifier getIdentifier() {
        return Constants.ElytraHudIdentifier;
    }

}
