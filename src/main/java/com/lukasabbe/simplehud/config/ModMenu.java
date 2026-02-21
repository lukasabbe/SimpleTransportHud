package com.lukasabbe.simplehud.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.gui.controllers.cycling.EnumController;
import dev.isxander.yacl3.gui.controllers.slider.IntegerSliderController;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return this::createConfig;
    }

    private Screen createConfig(Screen parent) {
        Config instance = Config.HANDLER.instance();
        return YetAnotherConfigLib
                .createBuilder()
                .title(Component.translatable("simple_hud.config.title"))
                .category(ConfigCategory
                        .createBuilder()
                        .name(Component.translatable("simple_hud.config.category.general.name"))
                        .tooltip(Component.translatable("simple_hud.config.category.general.tooltip"))
                        .group(toggleOptions())
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("simple_hud.config.category.general.option.scale.name"))
                                .description(OptionDescription.of(Component.translatable("simple_hud.config.category.general.option.scale.description")))
                                .binding(10, () -> instance.hudScale, newVal -> instance.hudScale = newVal)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(0,20).step(1))
                                .build())
                        .build()
                )
                .category(ConfigCategory
                        .createBuilder()
                        .name(Component.translatable("simple_hud.config.category.elytra_options.name"))
                        .option(Option
                                .<SpeedEnum>createBuilder()
                                .name(Component.translatable("simple_hud.config.category.elytra_options.option.speed_enum.name"))
                                .description(OptionDescription.of(Component.translatable("simple_hud.config.category.elytra_options.option.speed_enum.description")))
                                .binding(SpeedEnum.kmh, () -> instance.speedEnumElytra, newVal -> instance.speedEnumElytra = newVal)
                                .customController(opt -> new EnumController<>(opt, SpeedEnum.class))
                                .build())
                        .build())
                .category(ConfigCategory
                        .createBuilder()
                        .name(Component.translatable("simple_hud.config.category.boat_hud.name"))
                        .option(Option
                                .<SpeedEnum>createBuilder()
                                .name(Component.translatable("simple_hud.config.category.elytra_options.option.speed_enum.name"))
                                .description(OptionDescription.of(Component.translatable("simple_hud.config.category.elytra_options.option.speed_enum.description")))
                                .binding(SpeedEnum.kmh, () -> instance.speedEnumBoat, newVal -> instance.speedEnumBoat = newVal)
                                .customController(opt -> new EnumController<>(opt, SpeedEnum.class))
                                .build())
                        .build())
                .save(() -> Config.HANDLER.save())
                .build().generateScreen(parent);
    }
    private OptionGroup toggleOptions(){
        var builder = OptionGroup
                .createBuilder()
                .name( Component.translatable("simple_hud.config.category.general.group.activated_huds.name"))
                .description(OptionDescription.of(Component.translatable("simple_hud.config.category.general.group.activated_huds.description")));

        Config instance = Config.HANDLER.instance();
        for(var transport : instance.HudActivatedList.entrySet()){
            builder.option(Option
                    .<Boolean>createBuilder()
                    .name(Component.translatable("simple_hud.config.category.general.group.activated_huds.option." + transport.getKey().split(":")[1] + ".name"))
                    .description(OptionDescription.of(Component.translatable("simple_hud.config.category.general.group.activated_huds.option." + transport.getKey().split(":")[1] + ".description")))
                    .binding(true, transport::getValue, newVal -> instance.HudActivatedList.put(transport.getKey(), newVal))
                    .controller(TickBoxControllerBuilder::create)
                    .build()
            );
        }
        return builder.build();
    }
}
