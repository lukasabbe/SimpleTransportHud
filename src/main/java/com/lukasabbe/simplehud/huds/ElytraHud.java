package com.lukasabbe.simplehud.huds;

import com.lukasabbe.simplehud.Constants;
import com.lukasabbe.simplehud.config.Config;
import com.lukasabbe.simplehud.tools.ElytraTools;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Items;

public class ElytraHud implements SimpleHud {

    Identifier compass = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/compass.png");
    Identifier compass_pointer = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/compass_pointer.png");
    Identifier green_arrow = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/green_arrow.png");
    Identifier red_arrow = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/red_arrow.png");
    Identifier off_green_arrow = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/off_green_arrow.png");
    Identifier off_red_arrow = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/off_red_arrow.png");

    @Override
    public void render(GuiGraphics graphics, DeltaTracker tracker) {
        if(!isHudActivated()) return;
        if(!ElytraTools.isFlying()) return;
        if(client.noRender) return;
        if(client.player == null) return;

        int[] pos = getCornerPos();
        int x = pos[0];
        int y = pos[1];

        renderBackPlate(graphics);

        int textX = 5;
        float textScale = 0.6f;
        int whiteColor = 0xFFFFFFFF;

        //Draw pitch
        int pitchTextY = 5;
        int pitch = ElytraTools.getPitch();
        renderCenteredScaledText(graphics, String.format("%d°", pitch), x + textX, y + pitchTextY, whiteColor, textScale);

        //Draw speed
        int speedTextY = 15;
        renderCenteredScaledText(graphics, getSpeed(Config.HANDLER.instance().speedEnumElytra), x + textX, y + speedTextY, whiteColor, textScale);

        //Draw coordinates
        int coordinatesTextY = 25;
        int maxAvailableWidth = 40;
        String coordinatesText = String.format("%.0f:%.0f:%.0f", client.player.getX(), client.player.getY(), client.player.getZ());
        int textWidth = client.font.width(coordinatesText);
        float maxScale = (float) maxAvailableWidth / textWidth;
        float finalScale = Math.min(textScale, maxScale);
        renderCenteredScaledText(graphics, coordinatesText, x + textX, y + coordinatesTextY, whiteColor, finalScale);

        //Draw elytra status
        int itemX = 47;
        int itemY = 5;

        int statusBarY = 30 + y;
        int statusBarX = 50 + x;
        int statusSize = 17;
        int width = 2;

        float damagePercentage = ElytraTools.getDamagePercentage();
        final int statusBar = statusBarY - (int)(damagePercentage * statusSize);
        float damagePercentageLeft = 1 - damagePercentage;

        drawScaledItem(graphics, x + itemX, y + itemY, Items.ELYTRA, 0.5f);

        graphics.fill(statusBarX, statusBarY, statusBarX + width, statusBar, ARGB.color(0xFF, ElytraTools.damageColor()));
        if(damagePercentageLeft != 0) {
            graphics.fill(statusBarX, statusBar, statusBarX + width,statusBar - (int)(damagePercentageLeft * statusSize), 0xFF3D3D3D);
        }

        int textureCornerX = 0;
        int textureCornerY = 0;

        //Draw up and down arrows

        int arrowWidth = 6;
        int arrowHeight = 8;

        boolean isGoingUp = pitch > 0;
        int greenAndRedArrowX = x + 56;
        int greenArrowY = y + 10;
        int redArrowY = y + 20;

        if(!isGoingUp){
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    green_arrow,
                    greenAndRedArrowX, greenArrowY,
                    textureCornerX, textureCornerY,
                    arrowWidth, arrowHeight,
                    arrowWidth, arrowHeight
            );
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    off_red_arrow,
                    greenAndRedArrowX, redArrowY,
                    textureCornerX, textureCornerY,
                    arrowWidth, arrowHeight,
                    arrowWidth, arrowHeight
            );
        }else {
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    off_green_arrow,
                    greenAndRedArrowX, greenArrowY,
                    textureCornerX, textureCornerY,
                    arrowWidth, arrowHeight,
                    arrowWidth, arrowHeight
            );
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    red_arrow,
                    greenAndRedArrowX, redArrowY,
                    textureCornerX, textureCornerY,
                    arrowWidth, arrowHeight,
                    arrowWidth, arrowHeight
            );
        }

        //Compas background

        int compassWidth = 29;
        int compassHeight = 29;
        int compassX = x + 67;
        int compassY = y + 3;
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                compass,
                compassX, compassY,
                textureCornerX, textureCornerY,
                compassWidth, compassHeight,
                compassWidth, compassHeight
        );

        double radians = ElytraTools.getRadians();

        double radius = 5;
        float centerX = compassX + 14;
        float centerY = compassY + 14;
        float endX = Math.round(centerX + (radius * -Mth.sin(radians)));
        float endY = Math.round(centerY + (radius * Mth.cos(radians)));

        drawLine(graphics, centerX, centerY, endX, endY, (int) radius, compass_pointer);
    }

    @Override
    public Identifier getIdentifier() {
        return Constants.ElytraHudIdentifier;
    }

}
