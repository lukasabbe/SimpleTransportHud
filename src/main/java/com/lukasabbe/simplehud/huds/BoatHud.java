package com.lukasabbe.simplehud.huds;

import com.lukasabbe.simplehud.Constants;
import com.lukasabbe.simplehud.config.Config;
import com.lukasabbe.simplehud.tools.EntityTools;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.boat.Boat;

public class BoatHud implements SimpleHud {

    @Override
    public void render(GuiGraphics graphics, DeltaTracker tracker) {
        if(!isHudActivated()) return;
        if(client.noRender) return;
        if(!EntityTools.isRidingEntity(Boat.class)) return;
        if(client.player == null) return;

        int[] pos = getCornerPos();
        int x = pos[0];
        int y = pos[1];

        renderBackPlate(graphics);

        int textX = 5;
        float textScale = 0.6f;
        int whiteColor = 0xFFFFFFFF;

        //Draw boat angle
        int pitchTextY = 5;
        double angle = EntityTools.getAngle();
        renderCenteredScaledText(graphics, String.format("%3.0f °", angle), x + textX, y + pitchTextY, whiteColor, textScale);

        //Draw speed
        int speedTextY = 15;
        renderCenteredScaledText(graphics, getSpeed(Config.HANDLER.instance().speedEnumBoat), x + textX, y + speedTextY, whiteColor, textScale);

        //Draw coordinates
        int coordinatesTextY = 25;
        int maxAvailableWidth = 40;
        String coordinatesText = String.format("%.0f:%.0f:%.0f", client.player.getX(), client.player.getY(), client.player.getZ());
        int textWidth = client.font.width(coordinatesText);
        float maxScale = (float) maxAvailableWidth / textWidth;
        float finalScale = Math.min(textScale, maxScale);
        renderCenteredScaledText(graphics, coordinatesText, x + textX, y + coordinatesTextY, whiteColor, finalScale);

        int textureCornerX = 0;
        int textureCornerY = 0;

        int verticalArrowWidth = 8;
        int verticalArrowHeight = 6;

        int leftGreenArrowX = x + 40;
        int rightRedArrowX = x + 50;
        int verticalArrowY = y + 5;

        if(client.options.keyLeft.isDown()){
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    left_green_arrow,
                    leftGreenArrowX, verticalArrowY,
                    textureCornerX, textureCornerY,
                    verticalArrowWidth, verticalArrowHeight,
                    verticalArrowWidth, verticalArrowHeight
            );
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    right_off_red_arrow,
                    rightRedArrowX, verticalArrowY,
                    textureCornerX, textureCornerY,
                    verticalArrowWidth, verticalArrowHeight,
                    verticalArrowWidth, verticalArrowHeight
            );
        } else if (client.options.keyRight.isDown()) {
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    left_off_green_arrow,
                    leftGreenArrowX, verticalArrowY,
                    textureCornerX, textureCornerY,
                    verticalArrowWidth, verticalArrowHeight,
                    verticalArrowWidth, verticalArrowHeight
            );
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    right_red_arrow,
                    rightRedArrowX, verticalArrowY,
                    textureCornerX, textureCornerY,
                    verticalArrowWidth, verticalArrowHeight,
                    verticalArrowWidth, verticalArrowHeight
            );
        } else {
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    left_off_green_arrow,
                    leftGreenArrowX, verticalArrowY,
                    textureCornerX, textureCornerY,
                    verticalArrowWidth, verticalArrowHeight,
                    verticalArrowWidth, verticalArrowHeight
            );
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    right_off_red_arrow,
                    rightRedArrowX, verticalArrowY,
                    textureCornerX, textureCornerY,
                    verticalArrowWidth, verticalArrowHeight,
                    verticalArrowWidth, verticalArrowHeight
            );
        }

        int arrowWidth = 6;
        int arrowHeight = 8;

        int greenAndRedArrowX = x + 46;
        int greenArrowY = y + 13;
        int redArrowY = y + 23;

        if(client.options.keyUp.isDown()){
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
        } else if(client.options.keyDown.isDown()) {
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
        } else {
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
                    off_red_arrow,
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

        double radians = EntityTools.getRadians(tracker.getGameTimeDeltaPartialTick(true));

        double radius = 5;
        float centerX = compassX + 14;
        float centerY = compassY + 14;
        float endX = Math.round(centerX + (radius * -Mth.sin(radians)));
        float endY = Math.round(centerY + (radius * Mth.cos(radians)));

        drawLine(graphics, centerX, centerY, endX, endY, (int) radius, compass_pointer);

    }

    @Override
    public Identifier getIdentifier() {
        return Constants.BoatHudIdentifier;
    }
}
