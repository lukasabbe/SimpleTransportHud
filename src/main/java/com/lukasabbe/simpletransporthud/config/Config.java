package com.lukasabbe.simpletransporthud.config;

import com.lukasabbe.simpletransporthud.Constants;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;

import java.util.IdentityHashMap;
import java.util.Map;

public class Config {
    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler
            .createBuilder(Config.class)
            .id(Constants.ConfigIdentifier)
            .serializer(config -> GsonConfigSerializerBuilder
                    .create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("simple_transport_hud_config.json5"))
                    .build())
            .build();

    //Global

    @SerialEntry
    public Map<String, Boolean> HudActivatedList = getActiveHuds();

    @SerialEntry
    public boolean ignoreSafeArea = false;

    @SerialEntry
    public int hudScale = 10;

    //Hud positions
    @SerialEntry
    public HudPosition hudPositionElytra = HudPosition.CENTER;
    @SerialEntry
    public HudPosition hudPositionBoat = HudPosition.CENTER;
    @SerialEntry
    public HudPosition hudPositionMinecart = HudPosition.CENTER;
    @SerialEntry
    public HudPosition hudPositionHorse = HudPosition.CENTER;


    //Hud speed unit
    @SerialEntry
    public SpeedEnum speedEnumElytra = SpeedEnum.kmh;
    @SerialEntry
    public SpeedEnum speedEnumBoat = SpeedEnum.kmh;
    @SerialEntry
    public SpeedEnum speedEnumMinecart = SpeedEnum.kmh;
    @SerialEntry
    public SpeedEnum speedEnumHorse = SpeedEnum.kmh;

    //Display speed
    @SerialEntry
    public int elytraHudDelay = 2;
    @SerialEntry
    public int boatHudDelay = 0;
    @SerialEntry
    public int minecartHudDelay = 0;
    @SerialEntry
    public int horseHudDelay = 0;

    private Map<String, Boolean> getActiveHuds() {
        Map<String, Boolean> activatedHuds = new IdentityHashMap<>();
        for(var simpleHudIdentifier : Constants.HudIdentifiers){
            activatedHuds.put(simpleHudIdentifier.toShortString(), true);
        }
        return activatedHuds;
    }
}
