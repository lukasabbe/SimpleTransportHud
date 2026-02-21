package com.lukasabbe.simplehud.tools;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class ElytraTools {
    private static Vec3 oldPos = null;

    private static double speed = 0;

    private static long ticks = 0;

    public static void tickElytraTools(){
        calculateSpeed();
        if(!isFlying()){
            ticks = 0;
        }else{
            ticks++;
        }
    }

    public static long getTime(){
        return (ticks / 20);
    }

    public static void calculateSpeed(){
        var player = getLocalPlayer();
        if(player == null) return;
        if(oldPos == null){
            oldPos = player.getPosition(0f);
            return;
        }

        Vec3 newPos = player.getPosition(0f);
        double distance = newPos.distanceTo(oldPos);
        oldPos = newPos;
        speed = distance;
    }

    public static boolean isFlying(){
        var player = getLocalPlayer();
        if(player == null) return false;
        return player.isFallFlying();
    }

    public static int getPitch(){
        var player = getLocalPlayer();
        if(player == null) return 0;
        return (int)player.getXRot();
    }

    public static double getRadians(){
        var player = getLocalPlayer();
        if(player == null) return 0;
        float yaw = player.getViewYRot(Minecraft.getInstance().getFrameTimeNs());

        float normalizedYaw = (yaw % 360);
        if (normalizedYaw < 0) normalizedYaw += 360;

        return Math.toRadians(normalizedYaw);
    }

    public static float getDamagePercentage(){
        var player = getLocalPlayer();
        if(player == null) return 0;
        var chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
        if(!chestItem.is(Items.ELYTRA)) return 0;
        return (1 - ((float) chestItem.getDamageValue() / chestItem.getMaxDamage()));
    }

    public static int damageColor(){
        var player = getLocalPlayer();
        if(player == null) return 0;
        var chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
        if(!chestItem.is(Items.ELYTRA)) return 0;
        return chestItem.getBarColor();
    }

    private static @Nullable LocalPlayer getLocalPlayer() {
        return Minecraft.getInstance().player;
    }

    public static double getRawSpeed(){
        return speed;
    }
    public static double getSpeedMs(){
        return speed * 20;
    }
    public static double getSpeedKmh(){
        return getSpeedMs() * 3.6;
    }

    public static double getSpeedMph(){
        return getSpeedMs() * 2.23694;
    }
}
