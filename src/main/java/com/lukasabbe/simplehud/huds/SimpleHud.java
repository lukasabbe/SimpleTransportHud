package com.lukasabbe.simplehud.huds;

import com.lukasabbe.simplehud.Constants;
import com.lukasabbe.simplehud.SimpleHudMod;
import com.lukasabbe.simplehud.config.HudPosition;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public interface SimpleHud {
    Minecraft client = Minecraft.getInstance();
    Identifier backPlateAsset = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/backplate.png");

    void render(GuiGraphics graphics, DeltaTracker tracker);

    Identifier getIdentifier();

    default boolean isHudActivated(){
        return SimpleHudMod.configInstance.HudActivatedList.get(getIdentifier().toShortString());
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

        if(SimpleHudMod.configInstance.ignoreSafeArea){
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

    default int[] getCornerPos(){
        int backPlateCenteredX = 50;
        int backPlateCenteredY = 60;

        int screenWidth = client.getWindow().getGuiScaledWidth();
        int screenHeight = client.getWindow().getGuiScaledHeight();
        int[] pos = calculateHudPosition(screenWidth, screenHeight, SimpleHudMod.configInstance.hudPosition);
        int x = pos[0] - backPlateCenteredX;
        int y = pos[1] - backPlateCenteredY;
        return new int[]{x, y};
    }
}
