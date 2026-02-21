package com.lukasabbe.simplehud.tools;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import org.jspecify.annotations.Nullable;

public class EntityTools {
    public static boolean isRidingEntity(Class<?> entityType){
        var player = getLocalPlayer();
        if(player == null) return false;
        return getLocalPlayer().getVehicle() != null && entityType.isInstance(getLocalPlayer().getVehicle());
    }
    public static boolean isRidingEntity(){
        var player = getLocalPlayer();
        if(player == null) return false;
        return getLocalPlayer().getVehicle() != null;
    }

    public static double getAngle(){
        var player = getLocalPlayer();
        if(player == null) return 0;
        if(!isRidingEntity()) return 0;

        var vehicle = player.getVehicle();
        var velocity = vehicle.getDeltaMovement().multiply(1, 0, 1);
        var angle = Math.toDegrees(Math.acos(velocity.dot(vehicle.getLookAngle()) / velocity.length() * vehicle.getLookAngle().length()));
        if(Double.isNaN(angle)) angle = 0;
        return angle;
    }
    private static @Nullable LocalPlayer getLocalPlayer() {
        return Minecraft.getInstance().player;
    }

    public static double getRadians(float partialTick){
        var player = getLocalPlayer();
        if(player == null) return 0;
        if(!isRidingEntity()) return 0;
        float rawYaw = Mth.lerp(partialTick, player.getVehicle().yRotO, player.getVehicle().getYRot());

        float normalizedYaw = (rawYaw % 360);
        if (normalizedYaw < 0) normalizedYaw += 360;

        return Math.toRadians(normalizedYaw);
    }
}
