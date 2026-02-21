package com.lukasabbe.simplehud.huds;

import com.lukasabbe.simplehud.Constants;
import com.lukasabbe.simplehud.SimpleHudMod;
import com.lukasabbe.simplehud.config.Config;
import com.lukasabbe.simplehud.config.HudPosition;
import com.lukasabbe.simplehud.config.SpeedEnum;
import com.lukasabbe.simplehud.tools.ElytraTools;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;

public interface SimpleHud {
    Minecraft client = Minecraft.getInstance();
    Identifier backPlateAsset = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/backplate.png");
    Identifier compass = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/compass.png");
    Identifier compass_pointer = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/compass_pointer.png");
    Identifier left_green_arrow = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/left_green_arrow.png");
    Identifier right_red_arrow = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/right_red_arrow.png");
    Identifier left_off_green_arrow = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/off_left_green_arrow.png");
    Identifier right_off_red_arrow = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/off_left_red_arrow.png");
    Identifier green_arrow = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/green_arrow.png");
    Identifier red_arrow = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/red_arrow.png");
    Identifier off_green_arrow = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/off_green_arrow.png");
    Identifier off_red_arrow = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/off_red_arrow.png");

    void render(GuiGraphics graphics, DeltaTracker tracker);

    Identifier getIdentifier();

    default boolean isHudActivated(){
        return  Config.HANDLER.instance().HudActivatedList.get(getIdentifier().toShortString());
    }

    default void renderBackPlate(GuiGraphics graphics){
        int[] pos = getCornerPos();
        int x = pos[0];
        int y = pos[1];

        int backPlateCornerX = 0;
        int backPlateCornerY = 0;
        int backPlateWidth = 100;
        int backPlateHeight = 35;

        //Render Backplate
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                backPlateAsset,
                x, y,
                backPlateCornerX, backPlateCornerY,
                backPlateWidth, backPlateHeight,
                backPlateWidth, backPlateHeight
        );
    }
    // Thanks gen1nya for making the HUD pos system
    default int[] calculateHudPosition(int screenWidth, int screenHeight, HudPosition position){
        final int padding = 10;
        final int hudHalfWidth = 50;
        final int hudHeight = 36;

        if( Config.HANDLER.instance().ignoreSafeArea){
            return switch (position){
                case TOP_LEFT -> new int[]{hudHalfWidth + padding, 60 + padding};
                case TOP_RIGHT -> new int[]{screenWidth - hudHalfWidth - padding, 60 + padding};
                case BOTTOM_LEFT -> new int[]{hudHalfWidth + padding, screenHeight - padding * 2 + hudHeight};
                case BOTTOM_RIGHT -> new int[]{screenWidth - hudHalfWidth - padding, screenHeight - padding  * 2 + hudHeight};
                default -> new int[]{screenWidth / 2, screenHeight - 25}; // CENTER
            };
        }
        return switch (position) {
            case TOP_LEFT -> new int[]{hudHalfWidth + padding, 70 + padding};
            case TOP_RIGHT -> new int[]{screenWidth - hudHalfWidth - padding, 70 + padding};
            case BOTTOM_LEFT -> new int[]{hudHalfWidth + padding, screenHeight - 25};
            case BOTTOM_RIGHT -> new int[]{screenWidth - hudHalfWidth - padding, screenHeight - 25};
            default -> new int[]{screenWidth / 2, screenHeight - 25}; // CENTER
        };
    }

    default void renderCenteredScaledText(GuiGraphics graphics, String text, int centerX, int y, int color, float scale){
        var stack = graphics.pose();
        stack.pushMatrix();
        stack.translate(centerX, y);
        stack.scale(scale, scale);
        stack.translate(-centerX, -y);
        graphics.drawString(client.font, text, centerX, y, color);
        stack.popMatrix();
    }

    default void drawScaledItem(GuiGraphics context, int poxX, int posY, Item item, float scaled){
        var stack = context.pose();
        stack.pushMatrix();
        stack.translate(poxX,posY);
        stack.scale(scaled,scaled);
        stack.translate(-poxX,-posY);
        context.renderFakeItem(item.getDefaultInstance(), poxX, posY);
        stack.popMatrix();
    }

    default int[] getCornerPos(){
        int backPlateCenteredX = 50;
        int backPlateCenteredY = 60;

        int screenWidth = client.getWindow().getGuiScaledWidth();
        int screenHeight = client.getWindow().getGuiScaledHeight();
        int[] pos = calculateHudPosition(screenWidth, screenHeight, Config.HANDLER.instance().hudPositionElytra);
        int x = pos[0] - backPlateCenteredX;
        int y = pos[1] - backPlateCenteredY;
        return new int[]{x, y};
    }

    default void drawLine(GuiGraphics graphics, float posX, float posY, float endPosX, float endPosY, int points, Identifier texture){
        for (int i = 0; i <= points; i++){
            float progress = (float) i / points;
            float x = Mth.lerp(progress, posX, endPosX);
            float y = Mth.lerp(progress, posY, endPosY);
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    texture,
                    (int)x, (int)y,
                    0, 0,
                    1, 1,
                    1,5
            );
        }
    }

    default String getSpeed(SpeedEnum configSpeedEnum){
        return switch (configSpeedEnum){
            case kmh -> String.format("%.1f km/h", ElytraTools.getSpeedKmh());
            case mph -> String.format("%.1f mph", ElytraTools.getSpeedMph());
            case ms -> String.format("%.1f m/s", ElytraTools.getSpeedMs());
        };
    }
}
