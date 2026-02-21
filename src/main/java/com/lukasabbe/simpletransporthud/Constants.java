package com.lukasabbe.simpletransporthud;

import net.minecraft.resources.Identifier;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public final static String MOD_ID = "simpletransporthud";
    //Config
    public final static Identifier ConfigIdentifier = Identifier.fromNamespaceAndPath(MOD_ID, "config");
    //Hud:s
    public final static Identifier ElytraHudIdentifier = Identifier.fromNamespaceAndPath(MOD_ID, "elytra_hud");
    public final static Identifier BoatHudIdentifier = Identifier.fromNamespaceAndPath(MOD_ID, "boat_hud");
    public final static Identifier MinecartHudIdentifier = Identifier.fromNamespaceAndPath(MOD_ID, "minecart_hud");
    public final static Identifier HorseHudIdentifier = Identifier.fromNamespaceAndPath(MOD_ID, "horse_hud");

    public final static List<Identifier> HudIdentifiers = Arrays.asList(ElytraHudIdentifier, BoatHudIdentifier, MinecartHudIdentifier, HorseHudIdentifier);
}
